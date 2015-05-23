package com.tim.one.collaborator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.collaborator.ProjectRanger;
import com.tim.one.model.Project;

public class TestProjectRanger {

	@InjectMocks
	private ProjectRanger projectRanger = new ProjectRanger();
	
	@Mock
	private Project project;
	@Mock
	private Project otherProject;

	private Integer projectId = 1;
	private Integer otherProjectId = 2;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldAvoidDuplicates() throws Exception {
		Set<Project> byKeywordSet = new HashSet<Project>();
		Set<Project> byTag = new HashSet<Project>();
		
		byKeywordSet.add(project);
		byTag.add(otherProject);
		
		when(project.getId()).thenReturn(projectId);
		when(otherProject.getId()).thenReturn(otherProjectId);	
		
		Set<Project> result = projectRanger.distinct(byKeywordSet);
		assertEquals(1, result.size());
	}

}
