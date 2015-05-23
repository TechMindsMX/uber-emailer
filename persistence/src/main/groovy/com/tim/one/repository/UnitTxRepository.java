package com.tim.one.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tim.one.bean.TransactionType;
import com.tim.one.model.UnitTx;

public interface UnitTxRepository extends CrudRepository<UnitTx, Integer> {
  List<UnitTx> findUnitsByUserId(@Param("userId") Integer userId);
  List<UnitTx> findUnitsByProjectUnitSaleId(@Param("projectUnitSaleId") Integer projectUnitSaleId);
  List<UnitTx> findUnitsByprojectUnitSaleIdIn(List<Integer> projectUnitSales);
  List<UnitTx> findUnitsByTypeAndUserId(@Param("type") TransactionType type, @Param("userId") Integer userId);
  
  @Query("SELECT u FROM UnitTx u WHERE u.type=:type AND u.projectUnitSaleId IN :ids")
  List<UnitTx> findUnitsByTypeAndProjectUnitSales(@Param("type") TransactionType type, @Param("ids") List<Integer> ids);
  @Query("SELECT u FROM UnitTx u WHERE u.userId=:userId AND u.timestamp BETWEEN :startDate AND :endDate")
  List<UnitTx> findByUserAndDate(@Param("userId") Integer userId, @Param("startDate") Long startDate, @Param("endDate") Long endDate);
}
