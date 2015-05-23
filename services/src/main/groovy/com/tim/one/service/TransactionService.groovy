package com.tim.one.service

import com.tim.one.bean.BuyingSource
import com.tim.one.bean.PaymentType
import com.tim.one.bean.PurchaseTransactionBean
import com.tim.one.bean.ResultType
import com.tim.one.bean.TransactionBean
import com.tim.one.bean.TransactionType
import com.tim.one.bean.TransferUserLimitBean
import com.tim.one.model.Project
import com.tim.one.model.UserTx

interface TransactionService {
  
  Integer transferFunds(Integer senderId, Integer receiverId, BigDecimal amount)
  Integer createUnits(Integer userId, Integer projectId, Map<String, String> params, BuyingSource source)
  BigDecimal getAmountByUser(TransactionType type, Project project, Integer userId)
  List<PurchaseTransactionBean> getTransactions(Integer transactionId)
  ResultType createProviderPartnership(Integer providerId, Integer projectId, PaymentType paymentType)
  ResultType createProducerPartnership(Integer projectId)
  ResultType createProducerFunding(Integer projectId, BigDecimal amount)
  Integer measureAmountToTransfer(Integer userId, Integer destinationId, BigDecimal amount)
  List<TransferUserLimitBean> getLimitAmountsToTransfer(Integer userId)
  ResultType changeLimitAmountToTransfer(Integer userId, Integer destinationId)
  UserTx getUserTransaction(Integer transactionId)
  List<TransactionBean> getUserTransactions(Integer userId, Long start, Long end)  
  List<TransactionBean> getProviderTransactions(Integer userId, Long start, Long end)  
  List<TransactionBean> setBalances(List<TransactionBean> beans, BigDecimal balance)  
  List<TransactionBean> getUnitTransactions(Integer userId, Long startDate, Long endDate)  
  List<TransactionBean> getBulkTransactions(Integer bulkId)  
  List<TransactionBean> getProjectTransactions(Integer projectId)
  ResultType createReturnOfCapitalInjection(Integer projectId, Integer providerId, BigDecimal amount)

}
