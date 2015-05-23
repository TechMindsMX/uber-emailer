package com.tim.one.service

import com.tim.one.bean.TransactionType
import com.tim.one.model.ProjectFinancialData

interface UserTransactionService {
  
  void measureReturnOfInvestment(Integer userId, ProjectFinancialData projectFinancialData, BigDecimal amount, TransactionType type)
  BigDecimal getRDI(Integer userId, ProjectFinancialData project)
  BigDecimal getRDF(Integer userId, ProjectFinancialData projectFinancialData)
  BigDecimal getRAC(Integer userId, ProjectFinancialData projectFinancialData)
  
}
