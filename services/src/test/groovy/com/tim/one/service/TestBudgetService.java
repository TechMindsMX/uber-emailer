package com.tim.one.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.model.ProjectProvider;
import com.tim.one.service.impl.BudgetServiceImpl;

public class TestBudgetService {

	@InjectMocks
	private BudgetService budgetService = new BudgetServiceImpl();

	@Mock
	private ProjectProvider provider;
	
	private BigDecimal advanceQuantity = new BigDecimal("1000");
	private BigDecimal settlementQuantity = new BigDecimal("500");
	
	private Set<ProjectProvider> providers = new HashSet<ProjectProvider>();
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldGetBudget() throws Exception {
		BigDecimal expectedBudget = new BigDecimal("1500");
		providers.add(provider);
		when(provider.getAdvanceQuantity()).thenReturn(advanceQuantity);
		when(provider.getSettlementQuantity()).thenReturn(settlementQuantity);
		
		assertEquals(expectedBudget, budgetService.getBudget(providers));
	}

}
