package com.tim.one.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tim.one.bean.TramaAccountType;
import com.tim.one.model.TramaTx;

public interface TramaTxRepository extends CrudRepository<TramaTx, Integer> {
  TramaTx findByEntityIdAndType(@Param("entityId") Integer entityId, @Param("type") TramaAccountType type);
}
