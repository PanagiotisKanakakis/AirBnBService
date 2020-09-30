package com.main.rest.user;


import com.main.dto.user.*;
import com.main.entity.ResidenceEntity;
import com.main.entity.UserEntity;
import com.main.exception.AuthenticationException;
import com.main.exception.ConfigurationException;
import com.main.exception.RestException;
import com.main.exception.UserAlreadyExistsException;
import com.main.service.UserServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class UserApiImpl implements UserApi {

    @Autowired
    private UserServiceApi userServiceApi;

    @Override
    public UserEntity register(@RequestBody UserRegisterRequestDto userRegisterRequestDto) throws RestException {
        return userServiceApi.register(userRegisterRequestDto);
    }

    @Override
    public UserEntity login(@RequestBody UserLogInRequestDto userLogInRequestDto) throws RestException {
        return userServiceApi.login(userLogInRequestDto);
    }

    @Override
    public UserEntity getProfile(@RequestBody  UserUtilsDto userUtilsDto) throws RestException {
        return userServiceApi.getProfile(userUtilsDto);
    }

    @Override
    public UserEntity updateProfile(@RequestBody  UserUpdateProfileDto userUpdateProfileDto) throws RestException {
        return userServiceApi.updateProfile(userUpdateProfileDto);
    }

    @Override
    public List<ResidenceEntity> getUserResidences(@RequestBody UserUtilsDto userUtilsDto) throws RestException {
        return userServiceApi.getUserResidences(userUtilsDto);
    }

    @Override
    public List<ResidenceEntity> getRecommendedListings(@RequestBody UserUtilsDto userUtilsDto) throws RestException {
        return userServiceApi.getRecommendedListings(userUtilsDto);
    }

    @ExceptionHandler({UserAlreadyExistsException.class, ConfigurationException.class})
    private void conflict(UserAlreadyExistsException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.CONFLICT.value());
    }

    @ExceptionHandler({AuthenticationException.class})
    private void authentication(AuthenticationException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.UNAUTHORIZED.value());
    }

    @ExceptionHandler({RestException.class})
    private void generic(RestException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }



}
