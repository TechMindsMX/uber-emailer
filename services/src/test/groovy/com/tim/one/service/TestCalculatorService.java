package com.tim.one.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.bean.TransactionType;
import com.tim.one.collaborator.CalculatorCollaborator;
import com.tim.one.collaborator.TransactionDaoCollaborator;
import com.tim.one.collaborator.UnitCollaborator;
import com.tim.one.model.ProjectFinancialData;
import com.tim.one.model.ProjectUnitSale;
import com.tim.one.model.ProjectVariableCost;
import com.tim.one.model.UnitTx;
import com.tim.one.service.impl.CalculatorServiceImpl;
import com.tim.one.state.ApplicationState;

public class TestCalculatorService {

	@InjectMocks
	private CalculatorService calculatorService = new CalculatorServiceImpl();
	
	@Mock
	private ProjectFinancialData projectFianancialData;
	@Mock
	private ProjectVariableCost ticketService;
	@Mock
	private ProjectVariableCost isep;
	@Mock
	private ProjectUnitSale projectUnitSale;
	@Mock
	private UnitTx unit;
	@Mock
	private CalculatorCollaborator calculatorCollaborator;
	@Mock
	private TransactionDaoCollaborator transactionDaoCollaborator;
	@Mock
	private UnitCollaborator unitCollaborator;
	@Mock
	private Properties properties;

	private Set<ProjectVariableCost> variableCosts = new HashSet<ProjectVariableCost>();
	private Set<ProjectUnitSale> projectUnitSales = new HashSet<ProjectUnitSale>();
	private List<UnitTx> units = new ArrayList<UnitTx>();

	private Integer unitSaleId = 1;
	private Integer projectId = 2;

	private String mathPrecision = "10";

	private BigDecimal zero = new BigDecimal("0");
	private BigDecimal ita = new BigDecimal("40000");
	private BigDecimal totalVariableCost = new BigDecimal("10");
	private BigDecimal cva = new BigDecimal("100");

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(properties.getProperty(ApplicationState.MATH_PRECISION)).thenReturn(mathPrecision);
	}
	
	@Test
	public void shouldGetTRI() throws Exception {
		BigDecimal expectedResult = new BigDecimal("0.1975000000");
		
		setProjectExpectations();
		when(unitCollaborator.getUnitsAmount(projectFianancialData, units)).thenReturn(ita);

		setTriExpectations();
		
		assertEquals(expectedResult, calculatorService.getTRI(projectFianancialData));
	}
	
	@Test
	public void shouldGetZeroWhenNegativeTri() throws Exception {
		BigDecimal expectedResult = new BigDecimal("0.00");
		ita = new BigDecimal("10000");
		
		setProjectExpectations();
		when(unitCollaborator.getUnitsAmount(projectFianancialData, units)).thenReturn(ita);
		
		setTriExpectations();
		
		assertEquals(expectedResult, calculatorService.getTRI(projectFianancialData));
	}

	private void setTriExpectations() {
		when(projectFianancialData.getId()).thenReturn(projectId);
		when(unitCollaborator.getUnitsByProjectAndType(projectFianancialData, TransactionType.ALL)).thenReturn(units);
		when(calculatorCollaborator.getSumVariableCost(variableCosts)).thenReturn(totalVariableCost);
		when(calculatorCollaborator.computePercentage(ita, totalVariableCost)).thenReturn(cva);
	}
	
	@Test
	public void shouldGetTriWhenNoBudget() throws Exception {
		BigDecimal expectedResult = new BigDecimal("0.00");
		
		setProjectExpectations();
		when(projectFianancialData.getBalance()).thenReturn(zero);
		when(projectFianancialData.getBudget()).thenReturn(null);
		
		assertEquals(expectedResult, calculatorService.getTRI(projectFianancialData));
	}

	@Test
	public void shouldGetTriWhenNoProject() throws Exception {
		BigDecimal expectedResult = new BigDecimal("0.00");
		assertEquals(expectedResult, calculatorService.getTRI(null));
	}
	
	@Test
	public void shouldGetCRE() throws Exception {
		BigDecimal expectedResult = new BigDecimal("32022");
		setProjectExpectations();
		
		assertEquals(expectedResult, calculatorService.getCRE(projectFianancialData));
	}
	
	@Test
	public void shouldGetCreWhenNoProject() throws Exception {
		BigDecimal expectedResult = new BigDecimal("0");
		assertEquals(expectedResult, calculatorService.getCRE(null));
	}
	
	@Test
	public void shouldGetCreWhenNoBudget() throws Exception {
		BigDecimal expectedResult = new BigDecimal("0");
		
		setProjectExpectations();
		when(projectFianancialData.getBudget()).thenReturn(null);
		
		assertEquals(expectedResult, calculatorService.getCRE(projectFianancialData));
	}
	
	@Test
	public void shouldGetUCN() throws Exception {
		BigDecimal expectedResult = new BigDecimal("1000");
		projectUnitSales.add(projectUnitSale);
		units.add(unit);
		
		when(projectFianancialData.getId()).thenReturn(projectId);
		when(unitCollaborator.getUnitsByProjectAndType(projectFianancialData, TransactionType.CONSUMING)).thenReturn(units);
		when(projectUnitSale.getId()).thenReturn(unitSaleId);
		when(projectFianancialData.getProjectUnitSales()).thenReturn(projectUnitSales);
		when(unit.getProjectUnitSaleId()).thenReturn(unitSaleId);
		when(unit.getQuantity()).thenReturn(10);
		when(calculatorCollaborator.getUnitSale(unitSaleId, projectUnitSales)).thenReturn(new BigDecimal("100"));
		
		assertEquals(expectedResult, calculatorService.getUnitsByType(projectFianancialData, TransactionType.CONSUMING));
	}
	
	@Test
	public void shouldNotGetUCN() throws Exception {
		assertEquals(zero, calculatorService.getUnitsByType(null, TransactionType.CONSUMING));
	}
	
	private void setProjectExpectations() {
		when(ticketService.getValue()).thenReturn(new BigDecimal("12"));
		when(isep.getValue()).thenReturn(new BigDecimal("10"));
		when(projectFianancialData.getBudget()).thenReturn(new BigDecimal("32000"));
		when(projectFianancialData.getVariableCosts()).thenReturn(variableCosts);
		
		variableCosts.add(ticketService);
		variableCosts.add(isep);
	}
	
	@Test
	public void shouldGetCalculatedBreakeven() throws Exception {
		BigDecimal expectedResult = new BigDecimal("41025.6410290000");
		BigDecimal revenuePotential = new BigDecimal("115000");;
		setProjectExpectations();
		when(projectFianancialData.getRevenuePotential()).thenReturn(revenuePotential);
		
		assertEquals(expectedResult, calculatorService.getCalculatedBreakeven(projectFianancialData));
	}

}
