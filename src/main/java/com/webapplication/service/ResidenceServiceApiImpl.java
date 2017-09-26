package com.webapplication.service;

import com.webapplication.dao.ResidenceRepository;
import com.webapplication.dao.UserRepository;
import com.webapplication.dto.residence.*;
import com.webapplication.dto.user.UserUtilsDto;
import com.webapplication.entity.*;
import com.webapplication.error.ResidenceError;
import com.webapplication.error.UserError;
import com.webapplication.exception.AuthenticationException;
import com.webapplication.exception.ResidenceException;
import com.webapplication.exception.RestException;
import com.webapplication.mapper.ResidenceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

@Transactional
@Service
public class ResidenceServiceApiImpl implements ResidenceServiceApi {

    @Autowired
    private ResidenceMapper residenceMapper;
    @Autowired
    private ResidenceRepository residenceRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResidenceEntity addResidence(AddResidenceRequestDto addResidenceRequestDto) throws RestException {
        ResidenceEntity residenceEntity = residenceMapper.toResidenceEntity(addResidenceRequestDto);
        residenceEntity = residenceRepository.save(residenceEntity);
        System.out.println(residenceEntity.getResidenceId());
        System.out.println(residenceEntity.getType());
        return residenceEntity;
    }

    @Override
    public List<ResidenceEntity> searchResidence(SearchResidenceDto searchResidenceDto) throws RestException, IOException {
        String location = searchResidenceDto.getLocation();
        Integer capacity = searchResidenceDto.getCapacity();
        String username = searchResidenceDto.getUsername();
        Date arrivalDate = searchResidenceDto.getArrivalDate();
        Date departureDate = searchResidenceDto.getDepartureDate();
        List<ResidenceEntity> resultSet = new ArrayList<>();

        UserEntity user = userRepository.findUserEntityByUsername(username);
        if(user == null)
            throw new AuthenticationException(UserError.USER_NOT_EXISTS);

        SearchEntity searchEntity = new SearchEntity();
        searchEntity.setLocation(location);
        searchEntity.getUsers().add(user);
        user.getSearchedLocations().add(searchEntity);

        List<ResidenceEntity> rs =  residenceRepository.findByLocationOrCapacity(location,capacity);
        for(ResidenceEntity re : rs)
            if (isAvailable(re,arrivalDate,departureDate))
                resultSet.add(re);
        return rs;
    }

    private boolean isAvailable(ResidenceEntity re, Date arrivalDate, Date departureDate) {

        for(ReservationEntity res : re.getReservationInfo()){
            if ( res.getDepartureDate().before( new Date()) )
                continue;
            else
                if ( !((arrivalDate.after(res.getDepartureDate()) && departureDate.after(res.getDepartureDate()) ) || (arrivalDate.before(res.getArrivalDate()) && departureDate.before(res.getArrivalDate()) )) )
                    return false;
        }
        return true;
    }

    @Override
    public ResidenceEntity searchResidenceById(SearchResidenceByIdDto searchResidenceByIdDto) throws RestException {
        Integer residenceId = searchResidenceByIdDto.getResidenceId();
        ResidenceEntity residenceEntity = residenceRepository.findOne(residenceId);

        if(residenceEntity == null)
            throw new ResidenceException(ResidenceError.RESIDENCE_ID_NOT_EXISTS);
        return residenceEntity;
    }

    @Override
    public List<ResidenceEntity> getAllResidences() {
        List<ResidenceEntity> resultSet = new ArrayList<>();
        residenceRepository.findAll().forEach(resultSet::add);
        return resultSet;
    }

    @Override
    public ResidenceEntity addComment(AddCommentToResidenceDto addCommentToResidenceDto) throws RestException{
        Integer residenceId = addCommentToResidenceDto.getResidenceId();
        String comment = addCommentToResidenceDto.getComment();
        Integer grade = addCommentToResidenceDto.getGrade();
        ResidenceEntity residenceEntity = residenceRepository.findOne(residenceId);

        if(residenceEntity == null)
            throw new ResidenceException(ResidenceError.RESIDENCE_ID_NOT_EXISTS);

        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setComment(comment);
        commentEntity.setGrade(grade);
        commentEntity.setResidenceEntity(residenceEntity);

        residenceEntity.getComments().add(commentEntity);
        return residenceEntity;
    }

    @Override
    public ResidenceEntity updateResidence(ResidenceEntity residenceEntity) throws RestException {
        if ( residenceRepository.findOne(residenceEntity.getResidenceId()) == null  )
            throw new ResidenceException(ResidenceError.RESIDENCE_ID_NOT_EXISTS);
        return residenceRepository.save(residenceEntity);
    }

    @Override
    public void deleteResidence(ResidenceEntity residenceEntity) throws RestException{
        ResidenceEntity res = residenceRepository.findOne(residenceEntity.getResidenceId());

        if(res == null)
            throw new ResidenceException(ResidenceError.RESIDENCE_ID_NOT_EXISTS);

        List<UserEntity> users = res.getUsers();
        if(users.size() > 0){
            for(UserEntity u:users)
                u.getResidences().remove(res);
        }

        residenceRepository.delete(res);
    }

    @Override
    public List<ResidenceEntity> getResidencesBasedOnUserSearchedLocations(UserUtilsDto userUtilsDto) throws RestException{
        UserEntity user = userRepository.findUserEntityByUsername(userUtilsDto.getUsername());
        Set<ResidenceEntity> resultSet = new HashSet<>();
        List<SearchEntity> searchedResidences = user.getSearchedLocations();

        if( user == null )
            throw new AuthenticationException(UserError.USER_NOT_EXISTS);


        for(SearchEntity se : searchedResidences){
            List<ResidenceEntity> re = residenceRepository.findByLocationOrCapacity(se.getLocation(),null);
            for(ResidenceEntity r : re){
                if (! resultSet.contains(r))
                    resultSet.add(r);
            }
        }

        return new ArrayList<>(resultSet);
    }

    @Override
    public void reserveResidence(ReservationDto reservationDto) throws RestException{

        ReservationEntity re = new ReservationEntity();
        ResidenceEntity r = residenceRepository.findOne(reservationDto.getResidenceId());
        UserEntity u = userRepository.findUserEntityByUsername(reservationDto.getUsername());

        if( u == null )
            throw new AuthenticationException(UserError.USER_NOT_EXISTS);
        if(r == null)
            throw new ResidenceException(ResidenceError.RESIDENCE_ID_NOT_EXISTS);

        re.setResidenceEntity(r);
        re.setArrivalDate(reservationDto.getArrivalDate());
        re.setDepartureDate(reservationDto.getDepartureDate());
        u.getReservedResidences().add(re);
        r.getReservationInfo().add(re);
    }

}
