package com.main.service;

import com.main.dao.ResidenceRepository;
import com.main.dao.UserRepository;
import com.main.dto.user.UserLogInRequestDto;
import com.main.dto.user.UserRegisterRequestDto;
import com.main.dto.user.UserUpdateProfileDto;
import com.main.dto.user.UserUtilsDto;
import com.main.entity.ResidenceEntity;
import com.main.entity.UserEntity;
import com.main.error.UserError;
import com.main.exception.AuthenticationException;
import com.main.exception.ConfigurationException;
import com.main.exception.RestException;
import com.main.exception.UserAlreadyExistsException;
import com.main.mapper.UserMapper;
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
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class UserServiceApiImpl implements UserServiceApi {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ResidenceServiceApi residenceServiceApi;

//    @Autowired
//    private Clock clock;

    @Value("${security.algorithm}")
    private String algorithm;

    @Value("${security.secureRandomInstance}")
    private String secureRandomInstance;

    @Value("${security.derivedKeyLength} ")
    private Integer derivedKeyLength;

    @Value("${security.iterations}")
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

    @Override
    public List<ResidenceEntity> getUserResidences(UserUtilsDto userUtilsDto) {
        UserEntity user = userRepository.findUserEntityByUsername(userUtilsDto.getUsername());
        if (user == null)
            return null;
        return user.getResidences();
    }

    @Override
    public List<ResidenceEntity> getRecommendedListings(UserUtilsDto userUtilsDto) {
        return residenceServiceApi.getRecommentedResidences(userUtilsDto);
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
