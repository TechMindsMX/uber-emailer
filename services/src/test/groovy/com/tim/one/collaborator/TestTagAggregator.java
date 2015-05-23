package com.tim.one.collaborator;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.helper.ProjectHelper;
import com.tim.one.model.Project;
import com.tim.one.model.ProjectTag;

public class TestTagAggregator {
	
	@InjectMocks
	private TagAggregator tagAggregator = new TagAggregator();
	
	@Mock
	private Project project;
	@Mock
	private TagSplitter tagCollaborator;
	@Mock
	private ProjectHelper projectHelper;
	@Mock
	private ProjectTag projectTag; 
	
	private String tags = "tags";
	private String tag = "tag";
	private List<String> tagsAsList = new ArrayList<String>();
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldAddProjectTags() {
		tagsAsList.add(tag);
		
		when(tagCollaborator.split(tags)).thenReturn(tagsAsList);
		when(projectHelper.createProjectTag()).thenReturn(projectTag);
		
		tagAggregator.addProjectTags(project, tags);
		
		verify(projectTag).setTag(tag);
		verify(projectTag).setProject(project);
		verify(project).setTags(isA(Set.class));
	}
	
	@Test
	public void shouldNotAddProjectTags() {
		tagsAsList.add(StringUtils.EMPTY);
		
		when(tagCollaborator.split(tags)).thenReturn(tagsAsList);
		when(projectHelper.createProjectTag()).thenReturn(projectTag);
		
		tagAggregator.addProjectTags(project, tags);
		
		verify(projectTag, never()).setTag(tag);
		verify(projectTag, never()).setProject(project);
	}

}
