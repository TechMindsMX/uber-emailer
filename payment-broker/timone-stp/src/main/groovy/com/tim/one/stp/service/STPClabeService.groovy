package com.tim.one.stp.service

import com.tim.one.stp.exception.InvalidClabeException

interface STPClabeService {
  
  String generateSTPAccount(Integer userAccountId, String stpPrefix)
  
}
