package com.tim.one.service

import com.tim.one.bean.ResultType
import com.tim.one.model.UserAccount


interface STPAccountService {
 
  ResultType createAccout(Integer userId, Integer bankCode, String clabe)
  UserAccount getAccountByUserId(Integer userId)
 
}
