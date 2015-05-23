package com.tim.one.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tim.one.model.ProviderTx;

public interface ProviderTxRepository extends CrudRepository<ProviderTx, Integer> {
  @Query("SELECT p FROM ProviderTx p WHERE p.providerId=:userId AND p.timestamp BETWEEN :startDate AND :endDate")
  List<ProviderTx> findByUserAndDate(@Param("userId") Integer userId, @Param("startDate") Long startDate, @Param("endDate") Long endDate);
}
