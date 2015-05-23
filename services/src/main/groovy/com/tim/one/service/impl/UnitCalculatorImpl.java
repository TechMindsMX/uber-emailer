package com.tim.one.service.impl;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim.one.model.ProjectUnitSale;
import com.tim.one.repository.ProjectUnitSaleRepository;
import com.tim.one.service.UnitCalculator;

/**
 * @author josdem
 * @understands A class who knows how compute units
 * 
 */

@Service
public class UnitCalculatorImpl implements UnitCalculator {
	
	@Autowired
	private ProjectUnitSaleRepository projectUnitSaleRepository;
	
	private Log log = LogFactory.getLog(getClass());


	public BigDecimal getTotal(Map<String, String> params) {
		BigDecimal total = new BigDecimal("0");
		for (Map.Entry<String, String> mp : params.entrySet()) {
			log.info("key: " + mp.getKey() + " value: " + mp.getValue());
			ProjectUnitSale projectUnitSale = projectUnitSaleRepository.findOne(Integer.parseInt(mp.getKey()));
			BigDecimal value = null;
			try{
				value = new BigDecimal(mp.getValue());
			} catch (NumberFormatException nfe){
				log.info("CANNOT transform : " + mp.getValue());
				log.error(nfe, nfe);
			}
			total = total.add(projectUnitSale.getUnitSale().multiply(value));
		}
		return total;
	}

	public boolean sufficientUnits(Map<String, String> params) {
		for (Map.Entry<String, String> mp : params.entrySet()) {
			ProjectUnitSale projectUnitSale = projectUnitSaleRepository.findOne(Integer.parseInt(mp.getKey()));
			if(Integer.parseInt(mp.getValue()) > projectUnitSale.getUnit()){
				 return false;
			}
		}
		return true;
	}

}
