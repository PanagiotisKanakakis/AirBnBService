package com.webapplication.service;

import com.webapplication.authentication.Authenticator;
import com.webapplication.dao.UserRepository;
import com.webapplication.dto.user.*;
import com.webapplication.entity.UserEntity;
import com.webapplication.error.UserError;
import com.webapplication.exception.AuthenticationException;
import com.webapplication.exception.ConfigurationException;
import com.webapplication.exception.RestException;
import com.webapplication.exception.UserAlreadyExistsException;
import com.webapplication.mapper.UserMapper;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class UserServiceApiImpl implements UserServiceApi {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Authenticator authenticator;

    @Autowired
    private Clock clock;

    @Value("${algorithm}")
    private String algorithm;

    @Value("${secureRandomInstance}")
    private String secureRandomInstance;

    @Value("${derivedKeyLength} ")
    private Integer derivedKeyLength;

    @Value("${iterations}")
    private Integer iterations;

    @Override
    public UserEntity register(UserRegisterRequestDto userRegisterRequestDto) throws RestException {
        UserEntity userEntity = userRepository.findUserEntityByUsername(userRegisterRequestDto.getUsername());
        if (userEntity != null) {
            throw new UserAlreadyExistsException(UserError.USERNAME_ALREADY_EXISTS);
        }
        userEntity = userRepository.findUserEnityByEmail(userRegisterRequestDto.getEmail());
        if (userEntity != null) {
            throw new UserAlreadyExistsException(UserError.USER_EMAIL_ALREADY_EXISTS);
        }


        try {
            byte[] salt = createSaltForUser();
            String encodedSaltAsString = new String(Base64.encodeBase64(salt));
            String encodedPassword = encodePassword(userRegisterRequestDto.getPassword(), salt);
            userEntity = userMapper.toUserEntity(userRegisterRequestDto, encodedSaltAsString, encodedPassword);

            userRepository.save(userEntity);
        } catch (Exception e) {
            throw new ConfigurationException(UserError.CONFIGURATION_ERROR);
        }
        return userEntity;
        //return userMapper.toUserRegisterResponseDto(userEntity);
    }

    @Override
    public UserEntity login(UserLogInRequestDto userLogInRequestDto) throws RestException {
        UserEntity user = userRepository.findUserEntityByUsername(userLogInRequestDto.getUsername());
        Optional.ofNullable(user).orElseThrow(() -> new AuthenticationException(UserError.INVALID_CREDENTIALS));
        Boolean validated = validatePassword(userLogInRequestDto.getPassword(), user.getPassword(), user.getSalt());
        if (!validated) {
            throw new AuthenticationException(UserError.INVALID_CREDENTIALS);
        }
        SessionInfo sessionInfo = new SessionInfo(user.getUsername(), LocalDateTime.now(clock).plusMinutes(Authenticator.SESSION_TIME_OUT_MINUTES));
        UUID authToken = authenticator.createSession(sessionInfo);
        System.out.println(user.getUsername());
        return user;
        //return userMapper.toUserLogInResponseDto(user, authToken);
    }

    @Override
    public UserEntity getProfile(UserUtilsDto userUtilsDto) {
        UserEntity user = userRepository.findUserEntityByUsername(userUtilsDto.getUsername());
        return user;
        //return userMapper.toUserProfileDto(user);
    }

    @Override
    public UserEntity updateProfile(UserUpdateProfileDto userUpdateProfileDto) {
        UserEntity updatedUser = userMapper.toUpdatedUserEntity(userUpdateProfileDto);
        userRepository.save(updatedUser);
        return updatedUser;
        //return userMapper.toUserProfileDto(updatedUser);
    }

    private byte[] createSaltForUser() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstance(secureRandomInstance);
        byte[] salt = new byte[8];
        random.nextBytes(salt);
        return salt;
    }

    private String encodePassword(String password, byte[] salt) throws Exception {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, derivedKeyLength);
        SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);
        byte[] encodedPassword = Base64.encodeBase64(f.generateSecret(spec).getEncoded());
        return new String(encodedPassword);
    }

    private Boolean validatePassword(String attemptedPassword, String password, String saltStored) {
        try {
            byte[] salt = Base64.decodeBase64(saltStored.getBytes());
            KeySpec spec = new PBEKeySpec(attemptedPassword.toCharArray(), salt, iterations, derivedKeyLength);
            SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);
            String encodedAttemptedPassword = new String(Base64.encodeBase64(f.generateSecret(spec).getEncoded()));
            return password.equals(encodedAttemptedPassword);
        } catch (Exception e) {
            throw new ConfigurationException(UserError.CONFIGURATION_ERROR);
        }
    }

}
