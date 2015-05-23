package com.tim.one.collaborator;

import java.math.BigDecimal;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tim.one.model.ProjectUnitSale;
import com.tim.one.model.UnitTx;
import com.tim.one.repository.ProjectUnitSaleRepository;

@Component
public class MessagePackerCollaborator {

	@Autowired
	private ProjectUnitSaleRepository projectUnitSaleRepository;

	public BigDecimal getAmount(Set<UnitTx> units) {
		BigDecimal amount = new BigDecimal("0");
		for (UnitTx unitTx : units) {
			ProjectUnitSale projectUnitSale = projectUnitSaleRepository.findOne(unitTx.getProjectUnitSaleId());
			amount = amount.add(new BigDecimal(unitTx.getQuantity()).multiply(projectUnitSale.getUnitSale()));
		}
		return amount;
	}
	
}
