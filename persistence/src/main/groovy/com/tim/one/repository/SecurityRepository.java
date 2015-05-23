package com.tim.one.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tim.one.model.SessionKey;

public interface SecurityRepository extends CrudRepository<SessionKey, Integer> {
  SessionKey findByApiKey(@Param("apiKey") String apiKey);
}
