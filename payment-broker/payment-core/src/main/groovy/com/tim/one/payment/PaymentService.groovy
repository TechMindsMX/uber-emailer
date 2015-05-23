package com.tim.one.payment

import com.tim.one.payment.bean.PaymentBean

interface PaymentService {
  PaymentBean cashIn(Integer userId, String clabeOrdenante, String clabeBeneficiario, BigDecimal amount, String reference, String cancelUrl, String returnUrl)
  Integer cashOut(Integer userId, BigDecimal amount)
	String cashOut(String uuid, String clabe, Integer bankCode, BigDecimal amount)
}
