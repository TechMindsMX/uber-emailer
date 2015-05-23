package com.tim.one.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tim.one.model.UserTx;

public interface UserTxRepository extends CrudRepository<UserTx, Integer> {
  UserTx findById(@Param("id") Integer id);
  
  @Query("SELECT u FROM UserTx u WHERE (u.senderId=:userId OR u.receiverId=:userId) AND u.timestamp BETWEEN :startDate AND :endDate")
  List<UserTx> findByUserAndDate(@Param("userId") Integer userId, @Param("startDate") Long startDate, @Param("endDate") Long endDate);
}
