package com.tim.one.collaborator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tim.one.bean.TransactionType;
import com.tim.one.model.UnitTx;
import com.tim.one.repository.UnitTxRepository;

@Component
public class TransactionDaoCollaborator {
  
  @Autowired
  private UnitTxRepository unitTxRepository;

  public List<UnitTx> findUnits(TransactionType type, List<Integer> ids) {
    if(ids.isEmpty()){
      return new ArrayList<UnitTx>();
    }
    return type.equals(TransactionType.ALL) ? unitTxRepository.findUnitsByprojectUnitSaleIdIn(ids) : unitTxRepository.findUnitsByTypeAndProjectUnitSales(type, ids);
  }

}
