package com.main.service;

import com.main.dao.ResidenceRepository;
import com.main.dao.UserRepository;
import com.main.dto.comment.CommentDto;
import com.main.dto.residence.*;
import com.main.dto.user.UserUtilsDto;
import com.main.entity.*;
import com.main.error.ResidenceError;
import com.main.error.UserError;
import com.main.exception.AuthenticationException;
import com.main.exception.ResidenceException;
import com.main.exception.RestException;
import com.main.mapper.CommentMapper;
import com.main.mapper.ResidenceMapper;
import com.main.recommendation.Recommender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class ResidenceServiceApiImpl implements ResidenceServiceApi {

    @Autowired
    private ResidenceMapper residenceMapper;
    @Autowired
    private ResidenceRepository residenceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    Recommender recommender;

    @Override
    public ResidenceEntity addResidence(AddResidenceRequestDto addResidenceRequestDto) throws RestException {
        ResidenceEntity residenceEntity = residenceMapper.toResidenceEntity(addResidenceRequestDto);
        residenceEntity = residenceRepository.save(residenceEntity);
        return residenceEntity;
    }

    @Override
    public List<ResidenceEntity> searchResidence(SearchResidenceDto searchResidenceDto) throws RestException {
        String location = searchResidenceDto.getLocation();
        Integer capacity = searchResidenceDto.getCapacity();
        Integer wifi = searchResidenceDto.getWifi();
        Integer elevator = searchResidenceDto.getElevator();
        Integer kitchen = searchResidenceDto.getKitchen();
        Integer heating = searchResidenceDto.getHeating();
        Integer parking = searchResidenceDto.getParking();
        String username = searchResidenceDto.getUsername();
        Date arrivalDate = searchResidenceDto.getArrivalDate();
        Date departureDate = searchResidenceDto.getDepartureDate();
        List<ResidenceEntity> resultSet = new ArrayList<>();
        List<ResidenceEntity> sortedRs = null;
        if (!username.equals("guest")) {
            UserEntity user = userRepository.findUserEntityByUsername(username);
            if (user == null)
                throw new AuthenticationException(UserError.USER_NOT_EXISTS);
            if (!location.equals("all") && arrivalDate != null && departureDate != null) {
                SearchEntity searchEntity = new SearchEntity();
                searchEntity.setLocation(location);
                searchEntity.getUsers().add(user);
                user.getSearchedLocations().add(searchEntity);
            }

            if (capacity == -1 && location.equals("all")) {
                resultSet = user.getResidences();
            } else {
                List<ResidenceEntity> rs = residenceRepository.findByLocationOrCapacity(location, capacity);
                for (ResidenceEntity re : rs)
                    if (isAvailable(re, arrivalDate, departureDate))
                        resultSet.add(re);
            }
            sortedRs = resultSet.stream()
                    .sorted(Comparator.comparing(ResidenceEntity::getPrize).reversed())
                    .collect(Collectors.toList());

            if (wifi == 1)
                sortedRs = sortedRs.stream().filter(residenceEntity -> residenceEntity.getWifi() == 1).collect(Collectors.toList());
            if (elevator == 1)
                sortedRs = sortedRs.stream().filter(residenceEntity -> residenceEntity.getElevator() == 1).collect(Collectors.toList());
            if (kitchen == 1)
                sortedRs = sortedRs.stream().filter(residenceEntity -> residenceEntity.getKitchen() == 1).collect(Collectors.toList());
            if (heating == 1)
                sortedRs = sortedRs.stream().filter(residenceEntity -> residenceEntity.getHeating() == 1).collect(Collectors.toList());
            if (parking == 1)
                sortedRs = sortedRs.stream().filter(residenceEntity -> residenceEntity.getParking() == 1).collect(Collectors.toList());
        } else {
            List<ResidenceEntity> rs = residenceRepository.findByLocationOrCapacity(location, capacity);
            for (ResidenceEntity re : rs)
                if (isAvailable(re, arrivalDate, departureDate))
                    resultSet.add(re);
            sortedRs = resultSet.stream()
                    .sorted(Comparator.comparing(ResidenceEntity::getPrize).reversed())
                    .collect(Collectors.toList());

            if (wifi == 1)
                sortedRs = sortedRs.stream().filter(residenceEntity -> residenceEntity.getWifi() == 1).collect(Collectors.toList());
            if (elevator == 1)
                sortedRs = sortedRs.stream().filter(residenceEntity -> residenceEntity.getElevator() == 1).collect(Collectors.toList());
            if (kitchen == 1)
                sortedRs = sortedRs.stream().filter(residenceEntity -> residenceEntity.getKitchen() == 1).collect(Collectors.toList());
            if (heating == 1)
                sortedRs = sortedRs.stream().filter(residenceEntity -> residenceEntity.getHeating() == 1).collect(Collectors.toList());
            if (parking == 1)
                sortedRs = sortedRs.stream().filter(residenceEntity -> residenceEntity.getParking() == 1).collect(Collectors.toList());
        }
        return sortedRs;
    }

    private boolean isAvailable(ResidenceEntity re, Date arrivalDate, Date departureDate) {

        for (ReservationEntity res : re.getReservationInfo()) {
            System.out.println(res.getArrivalDate());
            System.out.println(arrivalDate);

            if ((arrivalDate.after(res.getArrivalDate()) && arrivalDate.before(res.getDepartureDate()))
                    || (departureDate.after(res.getArrivalDate()) && departureDate.before(res.getDepartureDate())))
                return false;

            if (arrivalDate.before(res.getArrivalDate()) && departureDate.after(res.getDepartureDate()))
                return false;


            return true;
            /*if ( res.getDepartureDate().before( new Date()) )
                continue;
            else
                if ( !((arrivalDate.after(res.getDepartureDate()) && departureDate.after(res.getDepartureDate()) ) || (arrivalDate.before(res.getArrivalDate()) && departureDate.before(res.getArrivalDate()) )) )
                    return false;*/
        }

        if (re.getReservationInfo() == null)
            if (arrivalDate.before(new Date()) || departureDate.before(new Date()))
                return false;

        return true;
    }

    @Override
    public AddResidenceResponseDto searchResidenceById(SearchResidenceByIdDto searchResidenceByIdDto) throws RestException {
        Integer residenceId = searchResidenceByIdDto.getResidenceId();
        Optional<ResidenceEntity> residenceEntity = residenceRepository.findById(residenceId);
        if (!residenceEntity.isPresent())
            throw new ResidenceException(ResidenceError.RESIDENCE_ID_NOT_EXISTS);
        return residenceMapper.toAddResidenceResponseDto(residenceEntity.get());
    }

    @Override
    public List<ResidenceEntity> getAllResidences() {
        List<ResidenceEntity> resultSet = new ArrayList<>();
        residenceRepository.findAll().forEach(resultSet::add);
        return resultSet;
    }

    @Override
    public void addComment(AddCommentToResidenceDto addCommentToResidenceDto) throws RestException {
        Integer residenceId = addCommentToResidenceDto.getResidenceId();
        String comment = addCommentToResidenceDto.getComment();
        Integer grade = addCommentToResidenceDto.getGrade();
        Optional<ResidenceEntity> residenceEntity = residenceRepository.findById(residenceId);
        UserEntity user = userRepository.findUserEntityByUsername(addCommentToResidenceDto.getUsername());

        if (!residenceEntity.isPresent())
            throw new ResidenceException(ResidenceError.RESIDENCE_ID_NOT_EXISTS);

        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setComment(comment);
        commentEntity.setGrade(grade);
        commentEntity.setResidenceEntity(residenceEntity.get());
        commentEntity.setUser(user);

        residenceEntity.get().getComments().add(commentEntity);
    }

    @Override
    public ResidenceEntity updateResidence(ResidenceEntity residence) throws RestException {
        ResidenceEntity updated_residence = residenceRepository.findById(residence.getResidenceId()).get();
        if (updated_residence.getResidenceId() == null)
            throw new ResidenceException(ResidenceError.RESIDENCE_ID_NOT_EXISTS);
        updated_residence.setResidenceId(residence.getResidenceId());
        updated_residence.setPrize(residence.getPrize());
        updated_residence.setBathrooms(residence.getBathrooms());
        updated_residence.setCapacity(residence.getCapacity());
        updated_residence.setBedrooms(residence.getBedrooms());
        updated_residence.setBeds(residence.getBeds());
        updated_residence.setElevator(residence.getElevator());
        updated_residence.setParking(residence.getParking());
        updated_residence.setHeating(residence.getHeating());
        updated_residence.setKitchen(residence.getKitchen());
        updated_residence.setWifi(residence.getWifi());

        return residenceRepository.save(updated_residence);
    }

    @Override
    public void deleteResidence(ResidenceEntity residenceEntity) throws RestException {
        ResidenceEntity res = residenceRepository.findById(residenceEntity.getResidenceId()).get();

        if (res == null)
            throw new ResidenceException(ResidenceError.RESIDENCE_ID_NOT_EXISTS);

        List<UserEntity> users = res.getUsers();
        if (users.size() > 0) {
            for (UserEntity u : users)
                u.getResidences().remove(res);
        }

        residenceRepository.delete(res);
    }

    @Override
    public List<ResidenceEntity> getResidencesBasedOnUserSearchedLocations(UserUtilsDto userUtilsDto) throws RestException {
        UserEntity user = userRepository.findUserEntityByUsername(userUtilsDto.getUsername());
        Set<ResidenceEntity> resultSet = new HashSet<>();
        List<SearchEntity> searchedResidences = user.getSearchedLocations();

        if (user == null)
            throw new AuthenticationException(UserError.USER_NOT_EXISTS);

        System.out.println("Users search list -> " + searchedResidences.size());
        if (searchedResidences.size() == 0) {
            return getAllResidences();
        } else {
            for (SearchEntity se : searchedResidences) {
                List<ResidenceEntity> re = residenceRepository.findByLocationOrCapacity(se.getLocation(), null);
                for (ResidenceEntity r : re) {
                    if (!resultSet.contains(r))
                        resultSet.add(r);
                }
            }
            if (resultSet.size() == 0)
                return getAllResidences();
        }
        return new ArrayList<>(resultSet);
    }

    @Override
    public ResidenceEntity reserveResidence(ReservationDto reservationDto) throws RestException {

        ReservationEntity re = new ReservationEntity();
        ResidenceEntity r = residenceRepository.findById(reservationDto.getResidenceId()).get();
        UserEntity u = userRepository.findUserEntityByUsername(reservationDto.getUsername());

        if (u == null)
            throw new AuthenticationException(UserError.USER_NOT_EXISTS);
        if (r == null)
            throw new ResidenceException(ResidenceError.RESIDENCE_ID_NOT_EXISTS);

        re.setResidenceEntity(r);
        System.out.println(reservationDto.getArrivalDate());
        re.setArrivalDate(reservationDto.getArrivalDate());
        re.setDepartureDate(reservationDto.getDepartureDate());
        u.getReservedResidences().add(re);
        r.getReservationInfo().add(re);

        return r;
    }

    @Override
    public List<CommentDto> getCommentOfResidence(SearchResidenceByIdDto searchResidenceByIdDto) throws RestException {
        ResidenceEntity r = residenceRepository.findById(searchResidenceByIdDto.getResidenceId()).get();
        return commentMapper.toCommentDto(r.getComments());
    }

    @Override
    public List<ResidenceEntity> getRecommentedResidences(UserUtilsDto userUtilsDto) {
        return recommender.run(userUtilsDto.getUsername());
    }

}
