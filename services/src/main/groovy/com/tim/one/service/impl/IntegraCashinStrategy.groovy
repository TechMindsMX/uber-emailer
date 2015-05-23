package com.tim.one.service.impl;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import com.tim.one.bean.IntegraTransactionType
import com.tim.one.model.IntegraUser
import com.tim.one.model.Persona
import com.tim.one.service.CashinStrategy
import com.tim.one.service.TransactionApplier
import com.tim.one.service.TransactionLogService
import com.tim.one.stp.command.IntegraCashinCommand
import com.tim.one.stp.formatter.CommandFormatter
import com.tim.one.stp.service.RestService
import com.tim.one.util.DateUtil;

/**
 * @author josdem
 * @understands A class who implements integra cashin 
 * 
 */

@Service
class IntegraCashinStrategy implements CashinStrategy {
	
	@Autowired
	private TransactionApplier transactionApplier;
	@Autowired
	private TransactionLogService transactionLogService;
	@Autowired
	private RestService restService;
	@Autowired
	private CommandFormatter commandFormatter;
	@Autowired
	private DateUtil dateUtil;

	@Override
	public void cashIn(Persona persona, BigDecimal amount, String reference, String clabeOrdenante) {
		IntegraUser user = (IntegraUser)persona;
		transactionApplier.addAmount(user, amount);
		String transactionId = transactionLogService.createIntegraUserLog(clabeOrdenante, user.getTimoneUuid(), amount, reference, IntegraTransactionType.CASH_IN);
		
		IntegraCashinCommand command = new IntegraCashinCommand();
		command.setReference(transactionId);
		command.setUuid(user.getTimoneUuid());
		command.setAmount(amount);
		command.setTimestamp(dateUtil.createDateAsLong());
		restService.postForObject(commandFormatter.format(command));
	}

}
