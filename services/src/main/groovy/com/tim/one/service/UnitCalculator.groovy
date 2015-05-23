package com.tim.one.service

interface UnitCalculator {
  
  BigDecimal getTotal(Map<String, String> params)
  boolean sufficientUnits(Map<String, String> params)
  
}
