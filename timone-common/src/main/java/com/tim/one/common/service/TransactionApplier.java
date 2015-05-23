package com.tim.one.common.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim.one.model.User;
import com.tim.one.repository.UserRepository;

@Service
public class TransactionApplier {

	@Autowired
	private UserRepository entityDao;
	@Autowired
	private TransactionLogService transactionLogService;

	public Boolean hasFunds(Integer userId, BigDecimal amount) {
		User user = entityDao.findUserById(userId);
		if (user.getBalance().compareTo(amount) < 0){
			return false;
		}
		return true;
	}

	public void substractAmount(Integer userId, BigDecimal amount) {
		User user = entityDao.findUserById(userId);
		user.setBalance(user.getBalance().subtract(amount));
		entityDao.save(user);
	}

	public void addAmount(Integer userId, BigDecimal amount) {
		User user = entityDao.findUserById(userId);
		user.setBalance(user.getBalance().add(amount));
		entityDao.save(user);
	}

}
