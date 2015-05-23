package com.tim.one.service

import com.tim.one.model.ProjectProvider

interface BudgetService {

  BigDecimal getBudget(Set<ProjectProvider> providers)
  
}
