package com.tim.one.service

import com.tim.one.model.ProjectFinancialData
import com.tim.one.model.ProjectProvider

interface BreakevenService {

  BigDecimal getCalculatedBreakeven(ProjectFinancialData project)

  BigDecimal getTramaFee(Set<ProjectProvider> providers)
  
}
