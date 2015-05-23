package com.tim.one.collaborator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tim.one.bean.TransactionType;
import com.tim.one.model.ProjectFinancialData;
import com.tim.one.model.ProjectUnitSale;
import com.tim.one.model.UnitTx;
import com.tim.one.repository.ProjectUnitSaleRepository;

@Component
public class UnitCollaborator {
	
	@Autowired
	private ProjectUnitSaleCollaborator projectUnitSaleCollaborator;
	@Autowired
	private TransactionDaoCollaborator transactionDaoCollaborator;
	@Autowired
  private ProjectUnitSaleRepository projectUnitSaleRepository;
	
	public BigDecimal getUnitsAmount(ProjectFinancialData project, List<UnitTx> units) {
		BigDecimal amount = new BigDecimal("0");
		for (UnitTx unitTx : units) {
			ProjectUnitSale projectUnitSale = projectUnitSaleRepository.findOne(unitTx.getProjectUnitSaleId());
			for (ProjectUnitSale sale : project.getProjectUnitSales()) {
			  if(projectUnitSale.getId().equals(sale.getId())){
			    amount = amount.add(new BigDecimal(unitTx.getQuantity()).multiply(projectUnitSale.getUnitSale()));
			  }
      }
		}
		return amount; 
	}
	
	public List<UnitTx> getUnitsByProjectAndType(ProjectFinancialData projectFinancialData, TransactionType type) {
		Set<ProjectUnitSale> projectUnitSales = projectFinancialData.getProjectUnitSales();
		List<Integer> projectUnitSalesIds = projectUnitSaleCollaborator.getIds(projectUnitSales);
		return transactionDaoCollaborator.findUnits(type, projectUnitSalesIds);
	}

}
