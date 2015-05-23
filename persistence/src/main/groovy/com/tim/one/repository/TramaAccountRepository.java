package com.tim.one.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tim.one.bean.AdminAccountType;
import com.tim.one.model.TramaAccount;

public interface TramaAccountRepository extends CrudRepository<TramaAccount, Integer> {
  TramaAccount findByType(@Param("type") AdminAccountType type);
}
