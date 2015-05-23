package com.tim.one.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tim.one.model.BulkUnitTx;

public interface BulkUnitTxRepository extends CrudRepository<BulkUnitTx, Integer> {
  BulkUnitTx findById(@Param("id") Integer id);
}
