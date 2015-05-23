package com.tim.one.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tim.one.model.StpStatus;

public interface StpStatusRepository extends CrudRepository<StpStatus, Integer> {
  StpStatus findById(@Param("id") Integer id);
}
