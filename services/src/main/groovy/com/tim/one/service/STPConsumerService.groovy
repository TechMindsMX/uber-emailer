package com.tim.one.service

import com.tim.one.bean.ResultType
import com.tim.one.model.BankCode
import com.tim.one.payment.PaymentService
import com.tim.one.payment.bean.PaymentBean

interface STPConsumerService extends PaymentService {
  
  PaymentBean cashIn(Integer userId, String clabeOrdenante, String clabeBeneficiario, BigDecimal amount, String reference, String cancelUrl, String returnUrl)
  Integer cashOut(Integer userId, BigDecimal amount)
	String cashOut(String uuid, String clabe, Integer bankCode, BigDecimal amount)
  ResultType confirmaOrden(Integer id, Integer claveRastreo, Integer estado, Long timestamp)
  Iterable<BankCode> listBankCodes()
  
}
