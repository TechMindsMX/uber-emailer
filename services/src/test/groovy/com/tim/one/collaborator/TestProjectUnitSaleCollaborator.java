package com.tim.one.collaborator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.collaborator.ProjectUnitSaleCollaborator;
import com.tim.one.exception.FormParamsException;
import com.tim.one.model.ProjectFinancialData;
import com.tim.one.model.ProjectUnitSale;

public class TestProjectUnitSaleCollaborator {

	@InjectMocks
	private ProjectUnitSaleCollaborator unitSaleCollaborator = new ProjectUnitSaleCollaborator();
	
	@Mock
	private ProjectUnitSale projectUnitSale;
	@Mock
	private ProjectFinancialData projectFinancialData;

	private Integer projectUnitsaleId = 1;
	private Set<ProjectUnitSale> projectUnitsales = new HashSet<ProjectUnitSale>();
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test (expected=FormParamsException.class)
	public void shouldThrowFormParamsExceptionDueToUnitSale() throws Exception {
		String section = "general,VIP";
		String unitSale = "100";
		String capacity = "1000,500";
		String codeSection = "A1";
		unitSaleCollaborator.createUnitSales(projectFinancialData, section, unitSale, capacity, codeSection);
	}
	
	@Test (expected=FormParamsException.class)
	public void shouldThrowFormParamsExceptionDueToSection() throws Exception {
		String section = "general";
		String unitSale = "100,20";
		String capacity = "1000,500";
		String codeSection = "A1, B1";
		unitSaleCollaborator.createUnitSales(projectFinancialData, section, unitSale, capacity, codeSection);
	}
	
	@Test (expected=FormParamsException.class)
	public void shouldThrowFormParamsExceptionDueToCapacity() throws Exception {
		String section = "general,VIP";
		String unitSale = "100,20";
		String capacity = "1000";
		String codeSection = "A1,B1";
		unitSaleCollaborator.createUnitSales(projectFinancialData, section, unitSale, capacity, codeSection);
	}
	
	@Test (expected=FormParamsException.class)
	public void shouldThrowFormParamsExceptionDueToCodeSection() throws Exception {
		String section = "general,VIP";
		String unitSale = "100,20";
		String capacity = "1000,500";
		String codeSection = "A1";
		unitSaleCollaborator.createUnitSales(projectFinancialData, section, unitSale, capacity, codeSection);
	}
	
	@Test
	public void shouldGetProjectUnitSales() throws Exception {
		String section = "general,VIP";
		String unitSale = "100,50";
		String capacity = "1000,500";
		String codeSection = "A1,B1";
		
		Set<ProjectUnitSale> result = unitSaleCollaborator.createUnitSales(projectFinancialData, section, unitSale, capacity, codeSection);

		assertEquals(2, result.size());
	}
	
	@Test
	public void shouldGetProjectUnitSalesWhenNoCodeSection() throws Exception {
		String section = "general,VIP";
		String unitSale = "100,50";
		String capacity = "1000,500";
		String codeSection = "";
		
		Set<ProjectUnitSale> result = unitSaleCollaborator.createUnitSales(projectFinancialData, section, unitSale, capacity, codeSection);
		
		assertEquals(2, result.size());
	}
	
	@Test
	public void shouldAcceptNoProjectUnitSales() throws Exception {
		String section = null;
		String unitSale = null;
		String capacity = null;
		String codeSection = null;
		
		Set<ProjectUnitSale> result = unitSaleCollaborator.createUnitSales(projectFinancialData, section, unitSale, capacity, codeSection);
		
		assertTrue(result.isEmpty());
	}
	
	@Test
	public void shouldGetProjectUnitsale() throws Exception {
		projectUnitsales.add(projectUnitSale);
		when(projectUnitSale.getId()).thenReturn(projectUnitsaleId);
		assertEquals(projectUnitSale, unitSaleCollaborator.getProjectUnitsale(projectUnitsaleId, projectUnitsales));
	}
	
	@Test
	public void shouldNotGetProjectUnitsale() throws Exception {
		assertNull(unitSaleCollaborator.getProjectUnitsale(projectUnitsaleId, projectUnitsales));
	}	
}
