package com.tim.one.collaborator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.tim.one.model.ProjectUnitSale;
import com.tim.one.model.ProjectVariableCost;
import com.tim.one.state.ApplicationState;

@Component
public class CalculatorCollaborator {
	
	@Autowired
  @Qualifier("properties")
	private Properties properties;

	public BigDecimal getUnitSale(Integer projectUnitSaleId, Set<ProjectUnitSale> projectUnitSales) {
		for (ProjectUnitSale projectUnitSale : projectUnitSales) {
			if (projectUnitSale.getId().equals(projectUnitSaleId)){
				return projectUnitSale.getUnitSale();
			}
		}
		return new BigDecimal("0");
	}

	public BigDecimal computePercentage(BigDecimal amount, BigDecimal percentage) {
		return percentage.multiply(amount).divide(new BigDecimal("100"), new Integer(properties.getProperty(ApplicationState.MATH_PRECISION)), RoundingMode.HALF_UP);
	}

	public BigDecimal getSumVariableCost(Set<ProjectVariableCost> variableCosts) {
		BigDecimal totalVariableCost = new BigDecimal("0");
		for (ProjectVariableCost projectVariableCost : variableCosts) {
			totalVariableCost = totalVariableCost.add(projectVariableCost.getValue());
		}
		return totalVariableCost;
	}

}
