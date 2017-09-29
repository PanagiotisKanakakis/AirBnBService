package com.webapplication.mapper;

import com.webapplication.dao.UserRepository;
import com.webapplication.dto.residence.AddResidenceRequestDto;
import com.webapplication.dto.residence.AddResidenceResponseDto;
import com.webapplication.entity.CommentEntity;
import com.webapplication.entity.PhotoEntity;
import com.webapplication.entity.ResidenceEntity;
import com.webapplication.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ResidenceMapper {

    @Autowired
    private UserRepository userRepository;

    public ResidenceEntity toResidenceEntity(AddResidenceRequestDto addResidenceRequestDto) {
        if (addResidenceRequestDto == null) {
            return null;
        }

        ResidenceEntity residenceEntity = new ResidenceEntity();
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
        addResidenceResponseDto.setUsername(residenceEntity.getUsers().get(0).getUsername());
        for(PhotoEntity photoEntity : residenceEntity.getPhotoPaths())
            addResidenceResponseDto.getPhotoPaths().add(photoEntity.getPath());

        for(CommentEntity commentEntity : residenceEntity.getComments())
            addResidenceResponseDto.getComments().add(commentEntity);

        return addResidenceResponseDto;
    }
}
