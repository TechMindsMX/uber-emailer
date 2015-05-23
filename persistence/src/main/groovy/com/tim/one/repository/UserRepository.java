package com.tim.one.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tim.one.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {
  User findUserByEmail(@Param("email") String email);
  User findUserByAccount(@Param("account") String account);
  
  @Query("SELECT u FROM User u, UserAccount a WHERE a.stpClabe=:stpClabe and u=a.user")
  User findUserByClabe(@Param("stpClabe") String stpClabe);
}
