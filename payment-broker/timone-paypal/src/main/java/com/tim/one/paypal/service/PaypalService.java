package com.tim.one.paypal.service;

import java.math.BigDecimal;

import com.tim.one.payment.PaymentService;
import com.tim.one.payment.bean.PaymentBean;
import com.tim.one.paypal.bean.PaypalBean;

public interface PaypalService extends PaymentService {
  
  PaymentBean cashIn(Integer userId, String clabeOrdenante, String clabeBeneficiario, BigDecimal amount, String reference, String cancelUrl, String returnUrl);

  void completePayment(PaypalBean paypalBean, String payerID);
  
}
