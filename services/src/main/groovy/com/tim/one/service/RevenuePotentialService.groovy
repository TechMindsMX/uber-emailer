package com.tim.one.service

import com.tim.one.model.ProjectUnitSale

interface RevenuePotentialService {
  
  BigDecimal getRevenuePotential(Set<ProjectUnitSale> projectUnitSales)
  
}
