package com.tim.one.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tim.one.bean.TransactionType;
import com.tim.one.collaborator.CalculatorCollaborator;
import com.tim.one.collaborator.UnitCollaborator;
import com.tim.one.model.ProjectFinancialData;
import com.tim.one.model.ProjectVariableCost;
import com.tim.one.model.UnitTx;
import com.tim.one.service.CalculatorService;
import com.tim.one.state.ApplicationState;

/**
 * @author josdem
 * @understands A class who knows how measure TRI CRE and BE
 * 
 */

@Service
public class CalculatorServiceImpl implements CalculatorService {
	
	@Autowired
	private CalculatorCollaborator calculatorCollaborator;
	@Autowired
	private UnitCollaborator unitCollaborator;
	@Autowired
	@Qualifier("properties")
	private Properties properties;
	
	public BigDecimal getTRI(ProjectFinancialData project) {
		BigDecimal zero = new BigDecimal("0.00");
		if (project == null || project.getBudget() == null){
			return zero;
		}
		List<UnitTx> units = unitCollaborator.getUnitsByProjectAndType(project, TransactionType.ALL);
		BigDecimal ita = unitCollaborator.getUnitsAmount(project, units);
		BigDecimal totalVariableCost = calculatorCollaborator.getSumVariableCost(project.getVariableCosts());
		BigDecimal cva = calculatorCollaborator.computePercentage(ita, totalVariableCost);
		BigDecimal cre = cva.add(project.getBudget());
		BigDecimal rdp = ita.subtract(cre);
		if(ita.compareTo(zero) == 0){
			return zero;
		}
		BigDecimal tri = rdp.divide(ita, new Integer(properties.getProperty(ApplicationState.MATH_PRECISION)), BigDecimal.ROUND_HALF_UP);
		return tri.compareTo(zero) > 0 ? tri : zero;
	}
	
	public BigDecimal getCRE(ProjectFinancialData project){
		BigDecimal zero = new BigDecimal("0");
		if (project == null || project.getBudget() == null){
			return zero;
		}
		BigDecimal totalVariableCost = new BigDecimal("0");
		for (ProjectVariableCost projectVariableCost : project.getVariableCosts()) {
			totalVariableCost = totalVariableCost.add(projectVariableCost.getValue());
		}
		BigDecimal budget = project.getBudget(); 
		return budget.add(totalVariableCost);
	}

	public BigDecimal getUnitsByType(ProjectFinancialData projectFinancialData, TransactionType type) {
		BigDecimal total = new BigDecimal("0");
		if (projectFinancialData == null){
			return total;
		}
		List<UnitTx> units = unitCollaborator.getUnitsByProjectAndType(projectFinancialData, type);
		for (UnitTx unitTx : units) {
			total = total.add(calculatorCollaborator.getUnitSale(unitTx.getProjectUnitSaleId(), projectFinancialData.getProjectUnitSales()).multiply(new BigDecimal(unitTx.getQuantity())));
		}
		return total;
	}
	
	public BigDecimal getCalculatedBreakeven(ProjectFinancialData project) {
		BigDecimal budget = project.getBudget();
		BigDecimal totalVariableCost = new BigDecimal("0");
		for (ProjectVariableCost projectVariableCost : project.getVariableCosts()) {
			totalVariableCost = totalVariableCost.add(projectVariableCost.getValue());
		}
		BigDecimal variableCostPercentage = totalVariableCost.divide(new BigDecimal("100"));
		
		BigDecimal subtract = new BigDecimal("1").subtract(variableCostPercentage);
		BigDecimal dividend = subtract.multiply(project.getRevenuePotential());
		return budget.divide(dividend, new Integer(properties.getProperty(ApplicationState.MATH_PRECISION)), RoundingMode.HALF_UP).multiply(project.getRevenuePotential());
	}

}
