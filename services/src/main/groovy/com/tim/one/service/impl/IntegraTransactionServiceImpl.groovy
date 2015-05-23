package com.tim.one.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import com.tim.one.bean.IntegraTransactionType
import com.tim.one.exception.NonSufficientFundsException
import com.tim.one.exception.UserNotFoundException
import com.tim.one.model.IntegraUserTx
import com.tim.one.repository.IntegraUserRepository
import com.tim.one.repository.IntegraUserTxRepository
import com.tim.one.service.IntegraTransactionService
import com.tim.one.service.TransactionApplier
import com.tim.one.service.TransactionLogService

/**
 * @author josdem
 * @understands A class who knows how to transfer from integra users
 *
 */

@Service
public class IntegraTransactionServiceImpl implements IntegraTransactionService {
	
	@Autowired
	IntegraUserRepository repository
	@Autowired
	TransactionApplier transactionApplier
	@Autowired
	TransactionLogService logService
	@Autowired
	IntegraUserTxRepository integraUserTxRepository

	@Override
	public String transfer(String uuidOrigin, String uuidDestination, BigDecimal amount) {
		def sender = repository.findByTimoneUuid(uuidOrigin)
		if (sender == null){
			throw new UserNotFoundException(1)
		}
		
		def receiver = repository.findByTimoneUuid(uuidDestination)
		if (receiver == null){
			throw new UserNotFoundException(1)
		}
		
		if(!transactionApplier.hasFunds(sender, amount)){
			throw new NonSufficientFundsException(1)
		}
		
		transactionApplier.substractAmount(sender, amount)
		transactionApplier.addAmount(receiver, amount)
		return logService.createIntegraUserLog(uuidOrigin, uuidDestination, amount, null, IntegraTransactionType.TRANSFER)
	}
	
	@Override
	public List<IntegraUserTx> getTransactionsByUuid(String uuid) {
		return integraUserTxRepository.findTransactionsByUuid(uuid);
	}

	@Override
	public IntegraUserTx getTransactionByUuid(String uuid) {
		return integraUserTxRepository.findByUuid(uuid);
	}

	@Override
	public List<IntegraUserTx> getTransactionsByUuidAndDate(String uuid, Long start, Long end) {
		return integraUserTxRepository.findTransactionsByUuidAndDate(uuid);
	}

	@Override
	public String cashIn(String uuid, BigDecimal amount) {
		def user = repository.findByTimoneUuid(uuid)
		if (user == null){
			throw new UserNotFoundException(1)
		}

		transactionApplier.addAmount(user, amount)
		return logService.createIntegraUserLog(uuid, uuid, amount, null, IntegraTransactionType.BANK_CASHIN)
	}

}
