package com.tim.one.service
import com.tim.one.model.Project
import com.tim.one.model.ProjectFinancialData

interface ProjectTransactionService {
  
  BigDecimal getTRI(ProjectFinancialData project)
  BigDecimal getTRF(ProjectFinancialData project)
  BigDecimal getMAC(Integer projectId)
  BigDecimal getCRE(ProjectFinancialData project)
  List<Project> getMoreProfitable(Integer max)
  
}
