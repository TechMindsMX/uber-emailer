package com.tim.one.collaborator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.bean.TransactionType;
import com.tim.one.collaborator.ProjectUnitSaleCollaborator;
import com.tim.one.collaborator.TransactionDaoCollaborator;
import com.tim.one.collaborator.UnitCollaborator;
import com.tim.one.model.ProjectFinancialData;
import com.tim.one.model.ProjectUnitSale;
import com.tim.one.model.UnitTx;
import com.tim.one.repository.ProjectUnitSaleRepository;

public class TestUnitCollaborator {

	@InjectMocks
	private UnitCollaborator unitCollaborator = new UnitCollaborator();
	
	@Mock
	private UnitTx unitTx;
	@Mock
	private ProjectUnitSale projectUnitSale;
	@Mock
	private ProjectUnitSaleCollaborator projectUnitSaleCollaborator;
	@Mock
	private List<Integer> ids;
	@Mock
	private TransactionDaoCollaborator transactionDaoCollaborator;
	@Mock
	private ProjectUnitSaleRepository projectUnitSaleRepository;
	@Mock
	private ProjectFinancialData projectFinancialData;

	private Integer projectUnitSaleId = 2;
	private Integer quantity = 3;
	
	private BigDecimal unitSale = new BigDecimal("100");
	private List<UnitTx> units = new ArrayList<UnitTx>();
	private Set<ProjectUnitSale> projectUnitSales = new HashSet<ProjectUnitSale>();
	private TransactionType type;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldGetUnitsAmount() throws Exception {
		BigDecimal expectedResult = new BigDecimal("300");
		units.add(unitTx);
		projectUnitSales.add(projectUnitSale);
		
		when(unitTx.getProjectUnitSaleId()).thenReturn(projectUnitSaleId);
		when(projectUnitSaleRepository.findOne(projectUnitSaleId)).thenReturn(projectUnitSale);
		when(unitTx.getQuantity()).thenReturn(quantity);
		when(projectUnitSale.getUnitSale()).thenReturn(unitSale);
		when(projectFinancialData.getProjectUnitSales()).thenReturn(projectUnitSales);
		
		assertEquals(expectedResult, unitCollaborator.getUnitsAmount(projectFinancialData, units));
	}
	
	@Test
	public void shouldGetUnitsByProjectAndType() throws Exception {
		when(projectUnitSaleCollaborator.getIds(projectUnitSales)).thenReturn(ids);
		when(transactionDaoCollaborator.findUnits(type, ids)).thenReturn(units);
		
		assertEquals(units, unitCollaborator.getUnitsByProjectAndType(projectFinancialData, type));
	}

}
