package com.tim.one.scheduler;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.model.Project;
import com.tim.one.model.ProjectFinancialData;
import com.tim.one.repository.ProjectFinancialDataRepository;
import com.tim.one.service.ProjectService;
import com.tim.one.state.ApplicationState;
import com.tim.one.util.DateUtil;

public class TestProjectScheduler {

	@InjectMocks
	private ProjectScheduler projectScheduler = new ProjectScheduler();
	
	@Mock
	private ProjectService projectService;
	@Mock
	private DateUtil dateUtil;
	@Mock
	private Project project;
	@Mock
	private ProjectFinancialData projectFinancialData;
	@Mock
	private Properties properties;
	@Mock
	private ProjectFinancialDataRepository projectFinancialDataRepository;

	private List<Project> projects = new ArrayList<Project>();

	private Integer projectId = 1;
	private Integer rechazadoStatusAsInteger = 4;
	private Integer noBreakevenReachedReasonAsInteger = 2;
	
	private String rechazadoStatus = "4";
	private String noBreakevenReachedReason = "2";
	private String noBreakevenReachedMessage = "No breakeven reached";

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		when(properties.getProperty(ApplicationState.RECHAZADO_STATUS)).thenReturn(rechazadoStatus);
		when(properties.getProperty(ApplicationState.NO_BREAKEVEN_REACHED_REASON)).thenReturn(noBreakevenReachedReason);
		when(properties.getProperty(ApplicationState.NO_BREAKEVEN_REACHED_MESSAGE)).thenReturn(noBreakevenReachedMessage);
	}
	
	@Test
	public void shouldChangeStatusAtVerifyCronBreakeven() throws Exception {
		Long now = 2L;
		Long fundEndDate = 1L;
		
		setCronBreakevenExpectations(now, fundEndDate);
		
		projectScheduler.cronBreakevenValidation();
		
		verify(projectService).changeStatus(projectId, rechazadoStatusAsInteger, null, noBreakevenReachedMessage, noBreakevenReachedReasonAsInteger);
	}
	
	
	@Test
	public void shouldNotChangeStatusAtVerifyCronBreakeven() throws Exception {
		Long now = 1L;
		Long fundEndDate = 2L;
		
		setCronBreakevenExpectations(now, fundEndDate);
		
		projectScheduler.cronBreakevenValidation();
		
		verify(projectService, never()).changeStatus(projectId, rechazadoStatusAsInteger, null, noBreakevenReachedMessage, noBreakevenReachedReasonAsInteger);
	}

	private void setCronBreakevenExpectations(Long now, Long fundEndDate) {
		projects.add(project);
		when(projectService.getProjectsByStatuses("0,1,2,3,5,10")).thenReturn(projects);
		when(project.getId()).thenReturn(projectId);
		when(projectFinancialDataRepository.findOne(projectId)).thenReturn(projectFinancialData);
		when(projectFinancialData.getFundEndDate()).thenReturn(fundEndDate);
		when(dateUtil.createDateAsLong()).thenReturn(now);
		when(projectFinancialData.getId()).thenReturn(projectId);
	}
	
	@Test
	public void shouldNotChangeStatusAtVerifyCronBreakevenWhenNoFinancialData() throws Exception {
		Long now = 1L;
		Long fundEndDate = 2L;
		
		setCronBreakevenExpectations(now, fundEndDate);
		when(projectFinancialDataRepository.findOne(projectId)).thenReturn(null);
		
		projectScheduler.cronBreakevenValidation();
		
		verify(projectService, never()).changeStatus(projectId, rechazadoStatusAsInteger, null, noBreakevenReachedMessage, noBreakevenReachedReasonAsInteger);
	}

}
