package com.tim.one.service

import com.tim.one.bean.ResultType

interface AccountService {
  
  String getAccount(String account)
  
  ResultType transferFundsFromTramaTo(String account, BigDecimal amount)
  
}
