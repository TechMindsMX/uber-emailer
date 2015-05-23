package com.tim.one.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service

import com.tim.one.bean.TransactionType;
import com.tim.one.model.Persona
import com.tim.one.model.User
import com.tim.one.service.CashinStrategy
import com.tim.one.service.TransactionApplier;
import com.tim.one.service.TransactionLogService;

/**
 * @author josdem
 * @understands A class who implements freakfund cashin 
 * 
 */

@Service
class FreakfundCashinStrategy implements CashinStrategy {
	
	@Autowired
	private TransactionApplier transactionApplier;
	@Autowired
	private TransactionLogService transactionLogService;

	@Override
	public void cashIn(Persona persona, BigDecimal amount, String reference, String clabeOrdenante) {
		User user = (User)persona;
		transactionApplier.addAmount(user, amount);
		transactionLogService.createUserLog(null, user.getId(), user.getId(), amount, reference, TransactionType.CASH_IN);
	}

}
