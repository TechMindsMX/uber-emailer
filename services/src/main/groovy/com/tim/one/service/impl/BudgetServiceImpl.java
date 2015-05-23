package com.tim.one.service.impl;

import java.math.BigDecimal;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.tim.one.model.ProjectProvider;
import com.tim.one.service.BudgetService;

/**
 * @author josdem
 * @understands A class who helps to measure the budget
 * 
 */

@Service
public class BudgetServiceImpl implements BudgetService {
	
	public BigDecimal getBudget(Set<ProjectProvider> providers) {
		BigDecimal total = new BigDecimal("0");
		for (ProjectProvider provider : providers) {
			total = total.add(provider.getAdvanceQuantity());
			total = total.add(provider.getSettlementQuantity());
		}
		return total;
	}

}
