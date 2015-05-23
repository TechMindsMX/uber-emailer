package com.tim.one.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tim.one.model.BankCode;

public interface BankCodeRepository extends CrudRepository<BankCode, Integer> {
  @Cacheable("predefinedFinders")
  BankCode findByBankCode(@Param("bankCode") Integer bankCode);
}
