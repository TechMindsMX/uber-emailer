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

import com.tim.one.model.ProjectUnitSale;
import com.tim.one.service.impl.RevenuePotentialServiceImpl;

public class TestRevenuePotentialService {

	@InjectMocks
	private RevenuePotentialService revenuePotentialService = new RevenuePotentialServiceImpl();
	
	@Mock
	private ProjectUnitSale projectUnitSale;
	
	private Integer units = 2;
	private BigDecimal unitSale = new BigDecimal("100");
	private Set<ProjectUnitSale> projectUnitSales = new HashSet<ProjectUnitSale>();

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldGetRevenuePotential() throws Exception {
		BigDecimal expectedTotal = new BigDecimal("200");
		projectUnitSales.add(projectUnitSale);
		
		when(projectUnitSale.getUnit()).thenReturn(units);
		when(projectUnitSale.getUnitSale()).thenReturn(unitSale);
		
		assertEquals(expectedTotal, revenuePotentialService.getRevenuePotential(projectUnitSales));
	}

}
