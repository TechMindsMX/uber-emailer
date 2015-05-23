package com.tim.one.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tim.one.model.TransferUserLimit;

public interface TransferUserLimitRepository extends CrudRepository<TransferUserLimit, Integer> {
  List<TransferUserLimit> findByUserId(@Param("userId") Integer userId);
  TransferUserLimit findByUserIdAndDestinationId(@Param("userId") Integer userId, @Param("destinationId") Integer destinationId);
}
