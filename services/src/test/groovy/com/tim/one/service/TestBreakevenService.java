package com.tim.one.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.collaborator.CalculatorCollaborator;
import com.tim.one.model.ProjectFinancialData;
import com.tim.one.model.ProjectProvider;
import com.tim.one.service.impl.BreakevenServiceImpl;
import com.tim.one.state.ApplicationState;

public class TestBreakevenService {

	@InjectMocks
	private BreakevenService breakevenService = new BreakevenServiceImpl();
	
	@Mock
	private ProjectFinancialData projectFinancialData;
	@Mock
	private CalculatorService calculatorService;
	@Mock
	private ProjectTransactionService projectTransactionService;
	@Mock
	private ProjectProvider provider;
	@Mock
	private CalculatorCollaborator calculatorCollaborator;
	@Mock
	private Properties properties;
	
	private String tramaFeePercentage = "5";

	private Set<ProjectProvider> providers = new HashSet<ProjectProvider>();

	private BigDecimal advanceQuantity = new BigDecimal("1000");
	private BigDecimal settlementQuantity = new BigDecimal("500");
	private BigDecimal tramaFee = new BigDecimal("75");

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldGetCalculatedBreakeven() throws Exception {
		BigDecimal calculatedBreakeven = new BigDecimal("100000");

		when(calculatorService.getCalculatedBreakeven(projectFinancialData)).thenReturn(calculatedBreakeven);
		
		assertEquals(calculatedBreakeven, breakevenService.getCalculatedBreakeven(projectFinancialData));
	}
	
	@Test
	public void shouldNotGetCalculatedBreakeven() throws Exception {
		BigDecimal expectedResult = new BigDecimal("0");
		assertEquals(expectedResult, breakevenService.getCalculatedBreakeven(null));
	}
	
	public void shouldGetTramaFee() throws Exception {
		BigDecimal total = advanceQuantity.add(settlementQuantity);
		providers.add(provider);
		
		when(properties.getProperty(ApplicationState.TRAMA_FEE_PERCENTAGE)).thenReturn(tramaFeePercentage);
		when(provider.getAdvanceQuantity()).thenReturn(advanceQuantity);
		when(provider.getSettlementQuantity()).thenReturn(settlementQuantity);
		when(calculatorCollaborator.computePercentage(total, new BigDecimal(tramaFeePercentage))).thenReturn(tramaFee);
		
		assertEquals(tramaFee, breakevenService.getTramaFee(providers));
	}
}
