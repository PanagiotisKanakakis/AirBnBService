package com.webapplication.service;


import com.webapplication.dto.user.*;
import com.webapplication.entity.UserEntity;
import com.webapplication.exception.RestException;

public interface UserServiceApi {

    UserEntity register(UserRegisterRequestDto userRegisterRequestDto) throws RestException;

    UserEntity login(UserLogInRequestDto userLogInRequestDto) throws RestException;

    UserEntity getProfile(UserUtilsDto userUtilsDto);

    UserEntity updateProfile(UserUpdateProfileDto userUpdateProfileDto);

}
