package com.tim.one.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim.one.bean.TransactionType;
import com.tim.one.model.UnitTx;
import com.tim.one.repository.UnitTxRepository;
import com.tim.one.service.UnitService;

/**
 * @author josdem
 * @understands A class who knows how to get units by type and user
 * 
 */

@Service
public class UnitServiceImpl implements UnitService {
	
	@Autowired
	private UnitTxRepository unitTxRepository;
	
	public List<UnitTx> getUnitsByUserAndType(Integer userId, TransactionType type) {
		return unitTxRepository.findUnitsByTypeAndUserId(type, userId);
	}

}
