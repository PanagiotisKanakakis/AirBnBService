package com.main.dao;

import com.main.entity.PhotoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends CrudRepository<PhotoEntity, Integer> {
}