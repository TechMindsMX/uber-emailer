package com.tim.one.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim.one.bean.TransactionType;
import com.tim.one.exception.UserNotFoundException;
import com.tim.one.model.User;
import com.tim.one.repository.UserRepository;
import com.tim.one.service.PaypalPaymentStorerService;
import com.tim.one.service.TransactionApplier;
import com.tim.one.service.TransactionLogService;

@Service
public class PaypalPaymentStorerServiceImpl implements PaypalPaymentStorerService {

	@Autowired
	private TransactionLogService transactionLogService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TransactionApplier transactionApplier;
	
	@Override
	public BigDecimal saveUserPayment(Integer userId, BigDecimal amount) {
		User user = userRepository.findOne(userId);
		if(user == null){
			throw new UserNotFoundException(userId);
		}
		transactionLogService.createUserLog(null, user.getId(), user.getId(), amount, null, TransactionType.CREDIT);
		transactionApplier.addAmount(user, amount);
		return user.getBalance();
	}

}
