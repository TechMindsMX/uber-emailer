package com.tim.one.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.bean.TransactionType;
import com.tim.one.collaborator.ProjectFilter;
import com.tim.one.collaborator.UnitCollaborator;
import com.tim.one.model.Project;
import com.tim.one.model.ProjectFinancialData;
import com.tim.one.model.ProjectVariableCost;
import com.tim.one.model.UnitTx;
import com.tim.one.repository.ProjectFinancialDataRepository;
import com.tim.one.repository.ProjectRepository;
import com.tim.one.repository.ProjectVariableCostRepository;
import com.tim.one.service.impl.ProjectTransactionServiceImpl;
import com.tim.one.state.ApplicationState;

public class TestProjectTransactionService {

	@InjectMocks
	private ProjectTransactionService projectTransactionService = new ProjectTransactionServiceImpl();
	
	@Mock
	private ProjectFinancialData projectFinancialData;
	@Mock
	private Set<ProjectVariableCost> projectVariableCosts;
	@Mock
	private CalculatorService calculatorService;
	@Mock
	private UnitCollaborator unitCollaborator;
	@Mock
	private Project project;
	@Mock
	private ProjectFilter projectFilter;
	@Mock
	private List<Project> projects;
	@Mock
	private Properties properties;
	@Mock
	private ProjectVariableCostRepository projectVariableCostRepository;
	@Mock
	private ProjectRepository projectRepository;
	@Mock
	private ProjectFinancialDataRepository projectFinancialDataRepository;
	
	private Integer projectId = 1;
	private Integer max = 2;
	private Integer produccionStatusAsInteger = 6;

	private String produccionStatus = "6";
	private String mathPrecision = "10";

	private BigDecimal ita = new BigDecimal("9450");
	private BigDecimal breakeven = new BigDecimal("50000");
	private BigDecimal ucn = new BigDecimal("136000");
	private BigDecimal ucf = new BigDecimal("500000");
	private BigDecimal zero = new BigDecimal("0");
	
	private List<Project> projectList = new ArrayList<Project>();
	private List<UnitTx> units = new ArrayList<UnitTx>();


	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldNotGetCRE() throws Exception {
		BigDecimal expectedResult = new BigDecimal("0");
		assertEquals(expectedResult, projectTransactionService.getCRE(null));
	}
	
	@Test
	public void shouldGetCRE() throws Exception {
		BigDecimal cre = new BigDecimal("100000");

		when(projectFinancialData.getId()).thenReturn(projectId);
		when(projectFinancialDataRepository.findOne(projectId)).thenReturn(projectFinancialData);
		when(projectFinancialData.getVariableCosts()).thenReturn(projectVariableCosts);
		when(calculatorService.getCRE(projectFinancialData)).thenReturn(cre);
		
		assertEquals(cre, projectTransactionService.getCRE(projectFinancialData));
	}
	
	@Test
	public void shouldGetMac() throws Exception {
		BigDecimal expectedResult = new BigDecimal("40550");
		
		when(projectFinancialDataRepository.findOne(projectId)).thenReturn(projectFinancialData);
		when(projectFinancialData.getId()).thenReturn(projectId);
		when(unitCollaborator.getUnitsByProjectAndType(projectFinancialData, TransactionType.ALL)).thenReturn(units);
		when(unitCollaborator.getUnitsAmount(projectFinancialData, units)).thenReturn(ita);
		when(projectFinancialData.getBreakeven()).thenReturn(breakeven);
		
		assertEquals(expectedResult, projectTransactionService.getMAC(projectId));
	}
	
	@Test
	public void shouldGetMoreProfitable() throws Exception {
		BigDecimal tri = new BigDecimal("100000");
		projectList.add(project);
		when(projectFinancialData.getId()).thenReturn(projectId);
		when(properties.getProperty(ApplicationState.PRODUCTION_STATUS)).thenReturn(produccionStatus);
		
		setTriExpectations(tri);
		when(project.getId()).thenReturn(projectId);
		when(projectRepository.findProjectsByStatus(produccionStatusAsInteger)).thenReturn(projectList);
		when(projectFilter.filter(projectList)).thenReturn(projects);
		
		assertEquals(projects, projectTransactionService.getMoreProfitable(max));
		verify(project).setTri(tri);
	}
	
	private void setTriExpectations(BigDecimal tri) {
	  when(projectFinancialData.getId()).thenReturn(projectId);
		when(projectFinancialDataRepository.findOne(projectId)).thenReturn(projectFinancialData);
		when(projectFinancialData.getVariableCosts()).thenReturn(projectVariableCosts);
		when(calculatorService.getTRI(projectFinancialData)).thenReturn(tri);
	}
	
	@Test
	public void shouldGetTRF() throws Exception {
		BigDecimal expectedTRF = new BigDecimal("27200.0000000000");
		BigDecimal tri = new BigDecimal("100000");
		when(calculatorService.getUnitsByType(projectFinancialData, TransactionType.CONSUMING)).thenReturn(ucn);
		when(calculatorService.getUnitsByType(projectFinancialData, TransactionType.FUNDING)).thenReturn(ucf);
		when(properties.getProperty(ApplicationState.MATH_PRECISION)).thenReturn(mathPrecision);

		setTriExpectations(tri);
		
		assertEquals(expectedTRF, projectTransactionService.getTRF(projectFinancialData));
	}
	
	@Test
	public void shouldNotGetTRF() throws Exception {
		BigDecimal expectedResult = new BigDecimal("0");
		assertEquals(expectedResult, projectTransactionService.getTRF(null));
	}
	
	@Test
	public void shouldNotGetTRFDueToNoUCF() throws Exception {
		BigDecimal tri = new BigDecimal("100000");
		when(calculatorService.getUnitsByType(projectFinancialData, TransactionType.CONSUMING)).thenReturn(ucn);
		when(calculatorService.getUnitsByType(projectFinancialData, TransactionType.FUNDING)).thenReturn(zero);

		setTriExpectations(tri);
		
		assertEquals(zero, projectTransactionService.getTRF(projectFinancialData));
	}
	
	@Test
	public void shouldGetTRI() throws Exception {
		BigDecimal tri = new BigDecimal("100000");

		when(projectFinancialData.getId()).thenReturn(projectId);
		setTriExpectations(tri);
		
		assertEquals(tri, projectTransactionService.getTRI(projectFinancialData));
	}
	
	@Test
	public void shouldNotGetTRI() throws Exception {
		BigDecimal expectedResult = new BigDecimal("0");
		assertEquals(expectedResult, projectTransactionService.getTRI(null));
	}
	
}
