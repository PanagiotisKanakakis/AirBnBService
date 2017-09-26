package com.webapplication.dao;

import com.webapplication.entity.PhotoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends CrudRepository<PhotoEntity, Integer> {
}