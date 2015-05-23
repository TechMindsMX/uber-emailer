package com.tim.one.collaborator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.collaborator.BeanAggregator;
import com.tim.one.collaborator.ProjectBusinessCaseCollaborator;
import com.tim.one.helper.ProjectHelper;
import com.tim.one.model.Project;
import com.tim.one.model.ProjectBusinessCase;
import com.tim.one.model.ProjectFinancialData;
import com.tim.one.model.ProjectPhoto;
import com.tim.one.repository.ProjectFinancialDataRepository;

public class TestBeanAggregator {

	@InjectMocks
	private BeanAggregator beanService = new BeanAggregator();
	
	@Mock
	private ProjectHelper projectHelper;
	@Mock
	private Project project;
	@Mock
	private ProjectBusinessCase projectBusinessCase;
	@Mock
	private ProjectPhoto projectPhoto;
	@Mock
	private Project projectFromDao;
	@Mock
	private ProjectBusinessCase projectBusinessCaseFromDao;
	@Mock
	private Set<ProjectPhoto> projectPhotos;
	@Mock
	private ProjectFinancialData projectFinancialData;
	@Mock
	private ProjectFinancialData projectFinancialDataFromDao;
	@Mock
	private ProjectFinancialDataRepository projectFinancialDataRepository;
	@Mock
	private ProjectBusinessCaseCollaborator projectBusinessCaseCollaborator;

	private String url = "url";
	private List<String> photoImages = new ArrayList<String>();

	private Integer projectId = 1;
	private Long timestamp = 1L;

	private Set<ProjectPhoto> filteredProjectPhotos = new HashSet<ProjectPhoto>();

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldAddProjectBusinessCase() throws Exception {
		when(projectBusinessCaseCollaborator.getProjectBusinessCase(projectFinancialData)).thenReturn(projectBusinessCase);
		
		beanService.addProjectBusinessCase(projectFinancialData, timestamp);
		
		verify(projectBusinessCase).setName(timestamp.toString());
		verify(projectFinancialData).setProjectBusinessCase(projectBusinessCase);
		verify(projectBusinessCase).setProjectFinancialData(projectFinancialData);
	}

	@Test
	public void shouldNotAddProjectBusinessCaseDueToNoName() throws Exception {
		beanService.addProjectBusinessCase(projectFinancialData, null);
		
		verify(projectBusinessCase, never()).setName(timestamp.toString());
		verify(projectFinancialData, never()).setProjectBusinessCase(projectBusinessCase);
		verify(projectBusinessCase, never()).setProjectFinancialData(projectFinancialData);
	}
	
	@Test
	public void shouldAddProjectPhotos() throws Exception {
		photoImages.add(url);
		
		when(project.getId()).thenReturn(projectId);
		when(projectFromDao.getProjectPhotos()).thenReturn(projectPhotos);
		when(projectHelper.createProjectPhotos()).thenReturn(filteredProjectPhotos);
		
		when(projectHelper.createProjectPhoto()).thenReturn(projectPhoto);
		
		beanService.addProjectPhoto(project, photoImages);
		
		verify(projectPhoto).setUrl(url);
		verify(project).setProjectPhotos(filteredProjectPhotos);
		assertEquals(1, filteredProjectPhotos.size());
	}
	
	@Test
	public void shouldNotAddProjectPhotos() throws Exception {
		when(projectFromDao.getProjectPhotos()).thenReturn(projectPhotos);
		when(project.getId()).thenReturn(projectId);
		
		beanService.addProjectPhoto(project, photoImages);
		
		verify(projectHelper, never()).createProjectPhoto();
	}

}
