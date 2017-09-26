package com.webapplication.mapper;

import com.webapplication.dao.RoleRepository;
import com.webapplication.dao.UserRepository;
import com.webapplication.dto.user.*;
import com.webapplication.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    public UserEntity toUserEntity(UserRegisterRequestDto userRegisterRequestDto, String encodedSalt, String encodedPassword) {
        if (userRegisterRequestDto == null) {
            return null;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userRegisterRequestDto.getUsername());
        userEntity.setPassword(encodedPassword);
        userEntity.setName(userRegisterRequestDto.getName());
        userEntity.setSurname(userRegisterRequestDto.getSurname());
        userEntity.setEmail(userRegisterRequestDto.getEmail());
        userEntity.setPhoneNumber(userRegisterRequestDto.getPhoneNumber());
        userEntity.setSalt(encodedSalt);
        userEntity.getMailbox().setUser(userEntity);
        List<RoleEntity> roles = new LinkedList<>();
        userRegisterRequestDto.getRoleDtos()
                .stream()
                .map(RoleDto::getDescription)
                .forEach(role -> roles.add(roleRepository.findRoleEntityByDescription(role)));
        userEntity.setRoles(roles);
        PhotoEntity photo = new PhotoEntity();
        photo.setPath(userRegisterRequestDto.getPhotoPath());
        photo.setUser(userEntity);
        userEntity.setProfilePhoto(photo);
        return userEntity;
    }

    public UserRegisterResponseDto toUserRegisterResponseDto(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        UserRegisterResponseDto userRegisterResponseDto = new UserRegisterResponseDto();
        userRegisterResponseDto.setUsername(userEntity.getUsername());
        userRegisterResponseDto.setPassword(userEntity.getPassword());
        userRegisterResponseDto.setName(userEntity.getName());
        userRegisterResponseDto.setSurname(userEntity.getSurname());
        userRegisterResponseDto.setEmail(userEntity.getEmail());
        userRegisterResponseDto.setPhoneNumber(userEntity.getPhoneNumber());
        userRegisterResponseDto.setRoleDtos(roleEntitiesToRoleDtos(userEntity.getRoles()));
        userRegisterResponseDto.setCity(userEntity.getCity());
        if(userEntity.getProfilePhoto() != null)
            userRegisterResponseDto.setPhoto(userEntity.getProfilePhoto().getPath());

        return userRegisterResponseDto;
    }

    public UserLogInResponseDto toUserLogInResponseDto(UserEntity userEntity, UUID authToken) {
        UserLogInResponseDto userLogInResponseDto = new UserLogInResponseDto();
        userLogInResponseDto.setUsername(userEntity.getUsername());
        userLogInResponseDto.setRoleDtos(roleEntitiesToRoleDtos(userEntity.getRoles()));
        userLogInResponseDto.setAuthToken(authToken);
        if(userEntity.getProfilePhoto() != null)
            userLogInResponseDto.setPhoto(userEntity.getProfilePhoto().getPath());

        return userLogInResponseDto;
    }


    private List<RoleDto> roleEntitiesToRoleDtos(List<RoleEntity> roles) {
        return roles.stream()
                .map(roleEntity -> RoleDto.valueOf(roleEntity.getDescription()))
                .collect(Collectors.toList());
    }

    public UserProfileDto toUserProfileDto(UserEntity user) {
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setEmail(user.getEmail());
        userProfileDto.setName(user.getName());
        userProfileDto.setSurname(user.getSurname());
        userProfileDto.setPhoneNumber(user.getPhoneNumber());
        userProfileDto.setCity(user.getCity());
        userProfileDto.setRoleDtos(roleEntitiesToRoleDtos(user.getRoles()));
        List<CommentEntity> comments = new ArrayList<>();
        for(ResidenceEntity r : user.getResidences())
            comments.addAll(r.getComments());

        userProfileDto.setComments(comments);
        if(user.getProfilePhoto() != null)
            userProfileDto.setPhoto(user.getProfilePhoto().getPath());
        return userProfileDto;
    }

    public UserEntity toUpdatedUserEntity(UserUpdateProfileDto userUpdateProfileDto) {
        UserEntity user = userRepository.findUserEntityByUsername(userUpdateProfileDto.getUsername());

        user.setName(userUpdateProfileDto.getName());
        user.setSurname(userUpdateProfileDto.getSurname());
        user.setCity(userUpdateProfileDto.getCity());
        user.setEmail(userUpdateProfileDto.getEmail());
        user.setPhoneNumber(userUpdateProfileDto.getPhoneNumber());
        List<RoleEntity> roles = new LinkedList<>();
        userUpdateProfileDto.getRoleDtos()
                .stream()
                .map(RoleDto::getDescription)
                .forEach(role -> roles.add(roleRepository.findRoleEntityByDescription(role)));
        user.setRoles(roles);

        if(user.getProfilePhoto() != null)
            user.getProfilePhoto().setPath(userUpdateProfileDto.getPhoto());
        else{
            PhotoEntity photo = new PhotoEntity();
            photo.setPath(userUpdateProfileDto.getPhoto());
            photo.setUser(user);
            user.setProfilePhoto(photo);
        }
        return user;
    }
}
