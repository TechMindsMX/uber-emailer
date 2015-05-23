package com.tim.one.collaborator;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.collaborator.UrlAggregator;
import com.tim.one.helper.ProjectHelper;
import com.tim.one.model.Project;
import com.tim.one.model.ProjectSoundcloud;
import com.tim.one.model.ProjectVideo;

public class TestUrlAggregator {

	@InjectMocks
	private UrlAggregator urlAggregator = new UrlAggregator();
	
	@Mock
	private Project project;
	@Mock
	private ProjectHelper projectHelper;
	@Mock
	private ProjectVideo projectYoutube;
	@Mock
	private ProjectSoundcloud projectSoundcloud;
	
	private String link = "link";
	private List<String> youtubeLinks = new ArrayList<String>();
	private List<String> soundcloudLinks = new ArrayList<String>();
	
	@Before
	public void testName() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldAddYoutubeLinks() throws Exception {
		youtubeLinks.add(link);
		when(projectHelper.createProjectYoutube()).thenReturn(projectYoutube);
		
		urlAggregator.addVideoLinks(project, youtubeLinks);
		
		verify(projectYoutube).setUrl(link);
		verify(projectYoutube).setProject(project);
		verify(project).setProjectVideos(isA(HashSet.class));
	}
	
	@Test
	public void shouldNotAddYoutubeLinksWhenIsBlank() throws Exception {
		youtubeLinks.add(StringUtils.EMPTY);
		when(projectHelper.createProjectYoutube()).thenReturn(projectYoutube);
		
		urlAggregator.addVideoLinks(project, youtubeLinks);
		
		verify(projectYoutube, never()).setUrl(link);
		verify(projectYoutube, never()).setProject(project);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldAddSoundcloudLinks() throws Exception {
		soundcloudLinks.add(link);
		when(projectHelper.createProjectSoundcloud()).thenReturn(projectSoundcloud);
		
		urlAggregator.addSoundcloudLinks(project, soundcloudLinks);
		
		verify(projectSoundcloud).setUrl(link);
		verify(projectSoundcloud).setProject(project);
		verify(project).setProjectSoundclouds(isA(HashSet.class));
	}
	
	@Test
	public void shouldNotAddSoundcloudLinksWhenIsBlank() throws Exception {
		soundcloudLinks.add(StringUtils.EMPTY);
		when(projectHelper.createProjectSoundcloud()).thenReturn(projectSoundcloud);
		
		urlAggregator.addSoundcloudLinks(project, soundcloudLinks);
		
		verify(projectSoundcloud, never()).setUrl(link);
		verify(projectSoundcloud, never()).setProject(project);
	}
	

}
