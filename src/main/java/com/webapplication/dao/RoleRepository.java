package com.webapplication.dao;


import com.webapplication.entity.RoleEntity;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<RoleEntity, Integer> {

    RoleEntity findRoleEntityByDescription(String description);

}
