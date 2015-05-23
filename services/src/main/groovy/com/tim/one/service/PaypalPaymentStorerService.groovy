package com.tim.one.service;

interface PaypalPaymentStorerService {

	BigDecimal saveUserPayment(Integer userId, BigDecimal amount)
	
}
