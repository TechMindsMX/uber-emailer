package com.tim.one.service

import com.tim.one.model.IntegraUser
import com.tim.one.model.ProjectFinancialData
import com.tim.one.model.TramaAccount
import com.tim.one.model.User

interface TransactionApplier {
  
  Boolean hasFunds(User user, BigDecimal amount)
  Boolean hasFunds(IntegraUser user, BigDecimal amount);
  void addAmount(User user, BigDecimal amount)
  void addAmount(ProjectFinancialData project, BigDecimal amount)
  void addAmount(TramaAccount trama, BigDecimal amount)
	void addAmount(IntegraUser user, BigDecimal amount)
  void substractAmount(User user, BigDecimal amount)
	void substractAmount(IntegraUser user, BigDecimal amount)
  void substractAmount(ProjectFinancialData project, BigDecimal amount)
  void substractAmount(TramaAccount trama, BigDecimal amount)
  
}
