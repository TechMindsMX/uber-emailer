package com.tim.one.service

interface SecurityService {
  
  String generateKey()
  boolean isValid(String token)

}
