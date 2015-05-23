package com.tim.one.service.impl;

import java.math.BigDecimal;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.tim.one.model.ProjectUnitSale;
import com.tim.one.service.RevenuePotentialService;

/**
 * @author josdem
 * @understands A class who knows how measure revenue potential
 * 
 */

@Service
public class RevenuePotentialServiceImpl implements RevenuePotentialService {
	
	public BigDecimal getRevenuePotential(Set<ProjectUnitSale> projectUnitSales) {
		BigDecimal total = new BigDecimal("0");
		for (ProjectUnitSale projectUnitSale : projectUnitSales) {
			total = total.add(new BigDecimal(projectUnitSale.getUnit()).multiply(projectUnitSale.getUnitSale()));
		}
		return total;
	}

}
