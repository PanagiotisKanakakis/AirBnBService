package com.main.mapper;

import com.main.dao.UserRepository;
import com.main.dto.residence.AddResidenceRequestDto;
import com.main.dto.residence.AddResidenceResponseDto;
import com.main.entity.CommentEntity;
import com.main.entity.PhotoEntity;
import com.main.entity.ResidenceEntity;
import com.main.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class ResidenceMapper {

    @Autowired
    private UserRepository userRepository;

    public ResidenceEntity toResidenceEntity(AddResidenceRequestDto addResidenceRequestDto) {
        if (addResidenceRequestDto == null) {
            return null;
        }

        ResidenceEntity residenceEntity = new ResidenceEntity();
//        residenceEntity.setResidenceId(Integer.parseInt(addResidenceRequestDto.getId()));
        residenceEntity.setAddress(addResidenceRequestDto.getAddress());
        residenceEntity.setGeoX(addResidenceRequestDto.getGeoX());
        residenceEntity.setGeoY(addResidenceRequestDto.getGeoY());
        residenceEntity.setCapacity(addResidenceRequestDto.getCapacity());
        residenceEntity.setPrize(addResidenceRequestDto.getPrize());
        residenceEntity.setType(addResidenceRequestDto.getType());
        residenceEntity.setRules(addResidenceRequestDto.getRules());
        residenceEntity.setDescription(addResidenceRequestDto.getDescription());
        residenceEntity.setBathrooms(addResidenceRequestDto.getBathrooms());
        residenceEntity.setSize(addResidenceRequestDto.getSize());
        residenceEntity.setBedrooms(addResidenceRequestDto.getBedrooms());
        residenceEntity.setBeds(addResidenceRequestDto.getBeds());
        residenceEntity.setLivingRoom(addResidenceRequestDto.getLivingRoom());
        residenceEntity.setLocation(addResidenceRequestDto.getLocation());
        residenceEntity.setTitle(addResidenceRequestDto.getTitle());
        residenceEntity.setBeds(addResidenceRequestDto.getBeds());
        residenceEntity.setWifi(addResidenceRequestDto.getWifi());
        residenceEntity.setKitchen(addResidenceRequestDto.getKitchen());
        residenceEntity.setHeating(addResidenceRequestDto.getHeating());
        residenceEntity.setParking(addResidenceRequestDto.getParking());
        residenceEntity.setElevator(addResidenceRequestDto.getElevator());
        UserEntity user = userRepository.findUserEntityByUsername(addResidenceRequestDto.getUsername());
        user.getResidences().add(residenceEntity);
        residenceEntity.setUsers(Collections.singletonList(user));
        for(String path : addResidenceRequestDto.getPhotoPaths()){
            PhotoEntity photoEntity = new PhotoEntity();
            photoEntity.setPath(path);
            photoEntity.setResidenceEntity(residenceEntity);
            residenceEntity.getPhotoPaths().add(photoEntity);
        }

        return residenceEntity;
    }

    public AddResidenceResponseDto toAddResidenceResponseDto(ResidenceEntity residenceEntity) {
        if (residenceEntity == null) {
            return null;
        }

        AddResidenceResponseDto addResidenceResponseDto = new AddResidenceResponseDto();
        addResidenceResponseDto.setAddress(residenceEntity.getAddress());
        addResidenceResponseDto.setBathrooms(residenceEntity.getBathrooms());
        addResidenceResponseDto.setBedrooms(residenceEntity.getBedrooms());
        addResidenceResponseDto.setCapacity(residenceEntity.getCapacity());
        addResidenceResponseDto.setGeoX(residenceEntity.getGeoX());
        addResidenceResponseDto.setGeoY(residenceEntity.getGeoY());
        addResidenceResponseDto.setDescription(residenceEntity.getDescription());
        addResidenceResponseDto.setLivingRoom(residenceEntity.getLivingRoom());
        addResidenceResponseDto.setLocation(residenceEntity.getLocation());
        addResidenceResponseDto.setPrize(residenceEntity.getPrize());
        addResidenceResponseDto.setResidenceId(residenceEntity.getResidenceId());
        addResidenceResponseDto.setRules(residenceEntity.getRules());
        addResidenceResponseDto.setType(residenceEntity.getType());
        addResidenceResponseDto.setSize(residenceEntity.getSize());
        addResidenceResponseDto.setBeds(residenceEntity.getBeds());
        addResidenceResponseDto.setTitle(residenceEntity.getTitle());
        addResidenceResponseDto.setUsername(residenceEntity.getUsers().get(0).getUsername());
        addResidenceResponseDto.setHeating(residenceEntity.getHeating());
        addResidenceResponseDto.setElevator(residenceEntity.getElevator());
        addResidenceResponseDto.setParking(residenceEntity.getParking());
        addResidenceResponseDto.setKitchen(residenceEntity.getKitchen());
        addResidenceResponseDto.setWifi(residenceEntity.getWifi());
        for(PhotoEntity photoEntity : residenceEntity.getPhotoPaths())
            addResidenceResponseDto.getPhotoPaths().add(photoEntity.getPath());

        for(CommentEntity commentEntity : residenceEntity.getComments())
            addResidenceResponseDto.getComments().add(commentEntity);

        addResidenceResponseDto.setReservationInfo(residenceEntity.getReservationInfo());
        return addResidenceResponseDto;
    }
}
