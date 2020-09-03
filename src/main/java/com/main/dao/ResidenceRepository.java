package com.main.dao;

import com.main.entity.ResidenceEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ResidenceRepository extends CrudRepository<ResidenceEntity, Integer> {

    List<ResidenceEntity> findByLocationOrCapacity(String location, Integer capacity);

}
