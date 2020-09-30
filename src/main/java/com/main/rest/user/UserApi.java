package com.main.rest.user;

import com.main.dto.user.*;
import com.main.entity.ResidenceEntity;
import com.main.entity.UserEntity;
import com.main.exception.RestException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public interface UserApi {

    @RequestMapping(path = "/register", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    UserEntity register(UserRegisterRequestDto userRegisterRequestDto) throws RestException;

    @RequestMapping(path = "/login", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    UserEntity login(UserLogInRequestDto userLogInRequestDto) throws RestException;

    @RequestMapping(path = "/getProfile", method = RequestMethod.POST,consumes = "application/json",produces = "application/json")
    UserEntity getProfile(UserUtilsDto userUtilsDto) throws RestException;

    @RequestMapping(path = "/updateProfile", method = RequestMethod.POST,consumes = "application/json",produces = "application/json")
    UserEntity updateProfile(UserUpdateProfileDto userUpdateProfileDto) throws RestException;

    @RequestMapping(path = "/getUserResidences", method = RequestMethod.POST,consumes = "application/json",produces = "application/json")
    List<ResidenceEntity> getUserResidences(UserUtilsDto userUtilsDto) throws RestException;

    @RequestMapping(path = "/getRecommendedListings", method = RequestMethod.POST,consumes = "application/json",produces = "application/json")
    List<ResidenceEntity> getRecommendedListings(UserUtilsDto userUtilsDto) throws RestException;
}
