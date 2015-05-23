package com.tim.one.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.collaborator.BeanAggregator;
import com.tim.one.collaborator.TagAggregator;
import com.tim.one.collaborator.UrlAggregator;
import com.tim.one.model.Project;
import com.tim.one.service.impl.ProjectPersisterServiceImpl;

public class TestProjectPersisterService {
	
	@InjectMocks
	private ProjectPersisterService projectPersisterService = new ProjectPersisterServiceImpl();
	
	@Mock
	private UrlAggregator urlService;
	@Mock
	private TagAggregator tagAggregator;
	@Mock
	private BeanAggregator beanAggregator;
	@Mock
	private StringSplitter stringSplitter;
	@Mock
	private ProjectService projectService;
	@Mock
	private List<String> urlList;
	@Mock
	private List<String> videoLinks;
	@Mock
	private List<String> soundcloudLinks;
	@Mock
	private Project project;
	
	private String photos = "photos";
	private String tags = "tags";
	private Integer userId = 1;


	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldSaveProject() throws Exception {
		when(stringSplitter.split(photos)).thenReturn(urlList);
		
		projectPersisterService.save(project, videoLinks, soundcloudLinks, photos, tags, userId);
		
		verify(urlService).addVideoLinks(project, videoLinks);
		verify(urlService).addSoundcloudLinks(project, soundcloudLinks);
		verify(tagAggregator).addProjectTags(project, tags);
		verify(beanAggregator).addProjectPhoto(project, urlList);
		verify(projectService).saveProject(project, userId);
	}

}
