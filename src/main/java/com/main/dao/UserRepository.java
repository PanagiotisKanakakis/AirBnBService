package com.main.dao;

import com.main.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer> {

    UserEntity findUserEntityByUsername(String username);

    UserEntity findUserEnityByEmail(String email);

    List<UserEntity> findAllByOrderByUsername();

}
