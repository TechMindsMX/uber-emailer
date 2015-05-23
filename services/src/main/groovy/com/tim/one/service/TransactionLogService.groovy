package com.tim.one.service

import com.tim.one.bean.EntityType
import com.tim.one.bean.IntegraTransactionType
import com.tim.one.bean.PaymentType
import com.tim.one.bean.ProjectTxType
import com.tim.one.bean.TramaAccountType
import com.tim.one.bean.TransactionType
import com.tim.one.model.BulkUnitTx
import com.tim.one.model.UnitTx

interface TransactionLogService {

	Integer createProjectLog(Integer projectId, Integer userId, BigDecimal amount, ProjectTxType type)
	Integer createUserLog(Integer projectId, Integer receiverId, Integer senderId, BigDecimal amount, String reference, TransactionType type)
	Integer createTramaLog(Integer entityId, BigDecimal amount, EntityType entityType, TramaAccountType type)
	UnitTx createUnitLog(Integer projectUnitSaleId, Integer quantity, Integer userId, BulkUnitTx bulkUnitTx, TransactionType type)
	void createProviderLog(Integer providerId, Integer projectId, BigDecimal amount, PaymentType paymentType, TransactionType type)
	void createStpLog(Integer id, Integer claveRastreo, Integer estado, Long timestamp, TransactionType type)
	String createIntegraUserLog(String uuidOrigin, String uuidDestination, BigDecimal amount, String reference, IntegraTransactionType type)

}

