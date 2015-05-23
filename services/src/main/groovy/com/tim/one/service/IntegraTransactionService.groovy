package com.tim.one.service

import com.tim.one.model.IntegraUserTx

interface IntegraTransactionService {
  
	String transfer(String uuidOrigin, String uuidDestination, BigDecimal amount)
	IntegraUserTx getTransactionByUuid(String uuid)
	List<IntegraUserTx> getTransactionsByUuid(String uuid)
	List<IntegraUserTx> getTransactionsByUuidAndDate(String uuid, Long start, Long end)
	String cashIn(String uuid, BigDecimal amount);
  
}
