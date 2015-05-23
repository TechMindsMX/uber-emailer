package com.tim.one.stp.service

import com.tim.one.stp.exception.SPEITransactionException

interface STPService {
  
  Integer registraOrden(String name, String email, Integer bankCode, String clabe, BigDecimal amount)
  
}
