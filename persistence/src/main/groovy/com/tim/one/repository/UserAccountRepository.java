package com.tim.one.repository;

import org.springframework.data.repository.CrudRepository;

import com.tim.one.model.User;
import com.tim.one.model.UserAccount;

public interface UserAccountRepository extends CrudRepository<UserAccount, Integer> {
  UserAccount findByUser(User user);
}
