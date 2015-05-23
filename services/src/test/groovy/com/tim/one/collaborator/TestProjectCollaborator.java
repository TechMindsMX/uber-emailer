package com.tim.one.collaborator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.collaborator.ProjectCollectionInitializer;
import com.tim.one.helper.ProjectHelper;
import com.tim.one.model.Project;
import com.tim.one.model.ProjectFinancialData;
import com.tim.one.model.ProjectUnitSale;
import com.tim.one.model.UnitTx;
import com.tim.one.repository.ProjectRepository;
import com.tim.one.repository.ProjectUnitSaleRepository;
import com.tim.one.repository.UnitTxRepository;
import com.tim.one.service.ProjectCollaboratorService;
import com.tim.one.service.impl.ProjectCollaboratorServiceImpl;

public class TestProjectCollaborator {

	@InjectMocks
	private ProjectCollaboratorService projectCollaborator = new ProjectCollaboratorServiceImpl();
	
	@Mock
	private UnitTx unitTx;
	@Mock
	private ProjectUnitSale projectUnitSale;
	@Mock
	private Project project;
	@Mock
	private ProjectRepository projectRepository;
	@Mock
	private ProjectUnitSaleRepository projectUnitSaleRepository;
	@Mock
	private UnitTxRepository unitTxRepository;
	@Mock
	private ProjectFinancialData projectFinancialData;
	@Mock
	private ProjectHelper projectHelper;
	@Mock
	private ProjectCollectionInitializer projectCollectionInitializer;
	
	private Integer projectUnitSaleId = 1;
	private Integer projectId = 2;
	private Integer userId = 3;
	private Integer autorizadoStatus = 5;
	private Integer quantity = 6;
	
	private List<Integer> statusList = new ArrayList<Integer>();
	private List<UnitTx> units = new ArrayList<UnitTx>();

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldGetProjectsFundedOrInvestedByUserId() throws Exception {
		setProjectFundedExpectations();
		
		Set<Project> result = projectCollaborator.getProjectsFundedOrInvestedByUserId(userId);
		
		assertEquals(1, result.size());
		assertTrue(result.contains(project));
	}
	
	private void setProjectFundedExpectations() {
		statusList.add(autorizadoStatus);
		units.add(unitTx);
		when(unitTx.getProjectUnitSaleId()).thenReturn(projectUnitSaleId);
		when(unitTxRepository.findUnitsByUserId(userId)).thenReturn(units);
		when(projectUnitSaleRepository.findOne(projectUnitSaleId)).thenReturn(projectUnitSale);
		when(projectUnitSale.getProjectFinancialData()).thenReturn(projectFinancialData);
		when(projectRepository.findOne(projectId)).thenReturn(project);
		when(projectFinancialData.getProject()).thenReturn(project);
	}
	
	@Test
	public void shouldGetTotalUnits() throws Exception {
		units.add(unitTx);
		when(unitTxRepository.findUnitsByProjectUnitSaleId(projectUnitSaleId)).thenReturn(units);
		when(unitTx.getQuantity()).thenReturn(quantity);
		
		assertEquals(quantity, projectCollaborator.getTotalUnits(projectUnitSaleId));
	}
	
	@Test
	public void shouldGetProjectFinancialDataWhenNoInProject() throws Exception {
		when(projectHelper.createProjectFinancialData()).thenReturn(projectFinancialData);
		projectCollaborator.getProjectFinancialData(project);
		verify(project).setProjectFinancialData(projectFinancialData);
	}
	
	@Test
	public void shouldGetProjectFinancialDataWhenInProject() throws Exception {
		when(project.getProjectFinancialData()).thenReturn(projectFinancialData);
		assertEquals(projectFinancialData, projectCollaborator.getProjectFinancialData(project));
	}
	
}
