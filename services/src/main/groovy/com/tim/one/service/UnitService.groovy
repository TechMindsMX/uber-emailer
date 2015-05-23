package com.tim.one.service

import com.tim.one.bean.TransactionType
import com.tim.one.model.UnitTx

interface UnitService {
  
  List<UnitTx> getUnitsByUserAndType(Integer userId, TransactionType type)
  
}
