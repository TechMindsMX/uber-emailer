package com.tim.one.service.impl;

import java.math.BigDecimal;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tim.one.collaborator.CalculatorCollaborator;
import com.tim.one.model.ProjectFinancialData;
import com.tim.one.model.ProjectProvider;
import com.tim.one.service.BreakevenService;
import com.tim.one.service.BudgetService;
import com.tim.one.service.CalculatorService;
import com.tim.one.service.ProjectTransactionService;
import com.tim.one.state.ApplicationState;

/**
 * @author josdem
 * @understands A class who knows how measure breakeven and trama fee
 * 
 */

@Service
public class BreakevenServiceImpl implements BreakevenService {
	
	@Autowired
	private CalculatorService calculatorService;
	@Autowired
	private ProjectTransactionService projectTransactionService;
	@Autowired
	private BudgetService budgetService;
	@Autowired
	private CalculatorCollaborator calculatorCollaborator;
	@Autowired
	@Qualifier("properties")
	private Properties properties;
	
	public BigDecimal getCalculatedBreakeven(ProjectFinancialData project) {
    return project != null ? calculatorService.getCalculatedBreakeven(project) : new BigDecimal("0");
	}

	public BigDecimal getTramaFee(Set<ProjectProvider> providers) {
		return calculatorCollaborator.computePercentage(budgetService.getBudget(providers), new BigDecimal(new Integer(properties.getProperty(ApplicationState.TRAMA_FEE_PERCENTAGE))));
	}

}
