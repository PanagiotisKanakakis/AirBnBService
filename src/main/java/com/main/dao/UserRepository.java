package com.main.dao;

import com.main.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer> {

    UserEntity findUserEntityByUsername(String username);

    UserEntity findUserEnityByEmail(String email);



}
