package com.main.dao;


import com.main.entity.RoleEntity;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<RoleEntity, Integer> {

    RoleEntity findRoleEntityByDescription(String description);

}
