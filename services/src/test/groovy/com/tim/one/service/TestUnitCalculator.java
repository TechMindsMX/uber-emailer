package com.tim.one.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.model.ProjectUnitSale;
import com.tim.one.repository.ProjectUnitSaleRepository;
import com.tim.one.service.impl.UnitCalculatorImpl;

public class TestUnitCalculator {

	@InjectMocks
	private UnitCalculator unitCalculator = new UnitCalculatorImpl();
	
	@Mock
	private ProjectUnitSale projectUnitSale;
	@Mock
	private ProjectUnitSaleRepository projectUnitSaleRepository;
	
	private Map<String, String> params = new HashMap<String, String>();

	private Integer projectUnitSaleId = 1;
	private String quantity = "2";
	private Integer units = 3;

	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldGetTotal() throws Exception {
		BigDecimal expectedResult = new BigDecimal("2000");
		BigDecimal unitSale = new BigDecimal("1000");
		setUnitSaleExpectations();
		when(projectUnitSale.getUnitSale()).thenReturn(unitSale);
		
		assertEquals(expectedResult, unitCalculator.getTotal(params));
	}
	
	@Test
	public void shouldKnowSufficientUnits() throws Exception {
		setUnitSaleExpectations();
		when(projectUnitSale.getUnit()).thenReturn(units);
		
		assertTrue(unitCalculator.sufficientUnits(params));
	}

	private void setUnitSaleExpectations() {
		params.put(projectUnitSaleId.toString(), quantity);
		when(projectUnitSaleRepository.findOne(projectUnitSaleId)).thenReturn(projectUnitSale);
	}
	
	@Test
	public void shouldKnowNonSufficientUnits() throws Exception {
		Integer units = 1;
		when(projectUnitSale.getUnit()).thenReturn(units);
		setUnitSaleExpectations();
		
		assertFalse(unitCalculator.sufficientUnits(params));
	}

}
