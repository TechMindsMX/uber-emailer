package com.tim.one.service

import com.tim.one.model.User

interface UserService {
  
  User getUserByEmail(String email)
  User getUserById(Integer userId)
  User getUserByAccount(String account)
  
}
