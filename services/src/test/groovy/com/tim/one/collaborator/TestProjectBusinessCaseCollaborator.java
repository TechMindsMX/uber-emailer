package com.tim.one.collaborator;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.helper.ProjectHelper;
import com.tim.one.model.ProjectBusinessCase;
import com.tim.one.model.ProjectFinancialData;

public class TestProjectBusinessCaseCollaborator {

	@InjectMocks
	private ProjectBusinessCaseCollaborator projectBusinessCaseCollaborator = new ProjectBusinessCaseCollaborator();
	
	@Mock
	private ProjectHelper projectHelper;
	@Mock
	private ProjectBusinessCase projectBusinessCase;

	private ProjectFinancialData projectFinancialData = new ProjectFinancialData();
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldGetProjectBusinessCaseFromProjectFinancialData() throws Exception {
		projectFinancialData.setProjectBusinessCase(projectBusinessCase);
		assertEquals(projectBusinessCase, projectBusinessCaseCollaborator.getProjectBusinessCase(projectFinancialData));
	}
	
	@Test
	public void shouldGetProjectBusinessCaseFromProjectHelper() throws Exception {
		when(projectHelper.createProjectBusinessCase()).thenReturn(projectBusinessCase);
		assertEquals(projectBusinessCase, projectBusinessCaseCollaborator.getProjectBusinessCase(projectFinancialData));
	}

}
