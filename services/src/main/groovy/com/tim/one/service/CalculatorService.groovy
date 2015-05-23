package com.tim.one.service

import com.tim.one.bean.TransactionType
import com.tim.one.model.ProjectFinancialData

interface CalculatorService {
	
  BigDecimal getTRI(ProjectFinancialData project)
  BigDecimal getCRE(ProjectFinancialData project)
  BigDecimal getCalculatedBreakeven(ProjectFinancialData project)
  BigDecimal getUnitsByType(ProjectFinancialData projectFinancialData, TransactionType type)
  
}
