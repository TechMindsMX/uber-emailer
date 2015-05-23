package com.tim.one.validator;

import static org.junit.Assert.*;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.state.ApplicationState;
import com.tim.one.util.NumericUtil;
import com.tim.one.validator.VariableCostsValidator;

public class TestVariableCostsValidator {
	
	@InjectMocks
	private VariableCostsValidator variableCostsValidator = new VariableCostsValidator();

	@Mock
	private NumericUtil numericUtil;
	@Mock
	private Properties properties;

	private String rentMin = "1";
	private String rentMax = "100";
	private String isepMin = "4";
	private String isepMax = "12";
	private String sacmMin = "6";
	private String sacmMax = "10";
	private String sogemMin = "1";
	private String sogemMax = "20";
	private String ticketServiceMin = "1";
	private String ticketServiceMax = "10";
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(numericUtil.isNumeric(isA(String.class))).thenReturn(true);
		when(properties.getProperty(ApplicationState.RENT_MIN)).thenReturn(rentMin);
		when(properties.getProperty(ApplicationState.RENT_MAX)).thenReturn(rentMax);
		when(properties.getProperty(ApplicationState.ISEP_MIN)).thenReturn(isepMin);
		when(properties.getProperty(ApplicationState.ISEP_MAX)).thenReturn(isepMax);
		when(properties.getProperty(ApplicationState.SACM_MIN)).thenReturn(sacmMin);
		when(properties.getProperty(ApplicationState.SACM_MAX)).thenReturn(sacmMax);
		when(properties.getProperty(ApplicationState.SOGEM_MIN)).thenReturn(sogemMin);
		when(properties.getProperty(ApplicationState.SOGEM_MAX)).thenReturn(sogemMax);
		when(properties.getProperty(ApplicationState.TICKET_SERVICE_MIN)).thenReturn(ticketServiceMin);
		when(properties.getProperty(ApplicationState.TICKET_SERVICE_MAX)).thenReturn(ticketServiceMax);
	}
	
	@Test
	public void shouldSaveVariableCosts() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("rent", "1");
		params.put("isep", "4");
		params.put("ticketService", "1");
		params.put("sacm", "6");
		params.put("sogem", "1");
		
		assertTrue(variableCostsValidator.isValid(params));
	}
	
	@Test
  public void shouldNotSaveIfEmpty() throws Exception {
	  Map<String, String> params = new HashMap<String, String>();
    assertFalse(variableCostsValidator.isValid(params));
  }
	
	@Test
	public void shouldNotSaveVariableCostsDueToLowRent() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("ticketService", "1");
		params.put("sacm", "6");
		params.put("sogem", "1");
		params.put("isep", "4");
		params.put("rent", "0");
		
		assertFalse(variableCostsValidator.isValid(params));
	}
	
	@Test
	public void shouldNotSaveVariableCostsDueToHighRent() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("ticketService", "1");
		params.put("sacm", "6");
		params.put("sogem", "1");
		params.put("isep", "4");
		params.put("rent", "101");
		
		assertFalse(variableCostsValidator.isValid(params));
	}
	
	@Test
	public void shouldNotSaveVariableCostsDueToLowIsep() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("ticketService", "1");
		params.put("sacm", "6");
		params.put("sogem", "1");
		params.put("rent", "1");
		params.put("isep", "3");
		
		assertFalse(variableCostsValidator.isValid(params));
	}
	
	@Test
	public void shouldNotSaveVariableCostsDueToHighIsep() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("ticketService", "1");
		params.put("sacm", "6");
		params.put("sogem", "1");
		params.put("rent", "1");
		params.put("isep", "13");
		
		assertFalse(variableCostsValidator.isValid(params));
	}
	
	@Test
	public void shouldNotSaveVariableCostsDueToLowSacm() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("ticketService", "1");
		params.put("sogem", "1");
		params.put("isep", "4");
		params.put("rent", "0");
		params.put("sacm", "0");
		
		assertFalse(variableCostsValidator.isValid(params));
	}
	
	@Test
	public void shouldNotSaveVariableCostsDueToHighSacm() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("ticketService", "1");
		params.put("sogem", "1");
		params.put("isep", "4");
		params.put("rent", "0");
		params.put("sacm", "11");
		
		assertFalse(variableCostsValidator.isValid(params));
	}
	
	@Test
	public void shouldNotSaveVariableCostsDueToLowSogem() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("ticketService", "1");
		params.put("sacm", "6");
		params.put("rent", "1");
		params.put("isep", "3");
		params.put("sogem", "0");
		
		assertFalse(variableCostsValidator.isValid(params));
	}
	
	@Test
	public void shouldNotSaveVariableCostsDueToHighSogem() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("ticketService", "1");
		params.put("sacm", "6");
		params.put("rent", "1");
		params.put("isep", "13");
		params.put("sogem", "21");
		
		assertFalse(variableCostsValidator.isValid(params));
	}
	
	@Test
	public void shouldNotSaveVariableCostsDueToLowTicketService() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("sacm", "6");
		params.put("rent", "1");
		params.put("isep", "3");
		params.put("sogem", "1");
		params.put("ticketServiceMin", "0");
		
		assertFalse(variableCostsValidator.isValid(params));
	}
	
	@Test
	public void shouldNotSaveVariableCostsDueToHighTicketService() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("sacm", "6");
		params.put("rent", "1");
		params.put("isep", "13");
		params.put("sogem", "1");
		params.put("ticketServiceMin", "11");
		
		assertFalse(variableCostsValidator.isValid(params));
	}
	
	@Test
	public void shouldSaveVariableCostsWithOnlyIsep() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("isep", "4");
		
		assertTrue(variableCostsValidator.isValid(params));
	}

	
	@Test
	public void shouldNotSaveVariableCostsDueToIsNotNumeric() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("isep", "4");
		when(numericUtil.isNumeric(isA(String.class))).thenReturn(false);
		
		assertFalse(variableCostsValidator.isValid(params));
	}

}
