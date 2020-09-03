package com.main.service;


import com.main.dto.user.*;
import com.main.entity.ResidenceEntity;
import com.main.entity.UserEntity;
import com.main.exception.RestException;

import java.util.List;

public interface UserServiceApi {

    UserEntity register(UserRegisterRequestDto userRegisterRequestDto) throws RestException;

    UserEntity login(UserLogInRequestDto userLogInRequestDto) throws RestException;

    UserEntity getProfile(UserUtilsDto userUtilsDto);

    UserEntity updateProfile(UserUpdateProfileDto userUpdateProfileDto);

    List<ResidenceEntity> getUserResidences(UserUtilsDto userUtilsDto);
}
