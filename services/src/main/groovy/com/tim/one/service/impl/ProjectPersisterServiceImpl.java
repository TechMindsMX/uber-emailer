package com.tim.one.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim.one.collaborator.BeanAggregator;
import com.tim.one.collaborator.TagAggregator;
import com.tim.one.collaborator.UrlAggregator;
import com.tim.one.model.Project;
import com.tim.one.service.ProjectPersisterService;
import com.tim.one.service.ProjectService;
import com.tim.one.service.StringSplitter;

@Service
public class ProjectPersisterServiceImpl implements ProjectPersisterService {
	
	@Autowired
	private UrlAggregator urlAggregator;
	@Autowired
	private StringSplitter stringSplitter;
	@Autowired
	private BeanAggregator beanAggregator;
	@Autowired
	private TagAggregator tagAggregator;
	@Autowired
	private ProjectService projectService;

	public void save(Project project, List<String> videoLinks, List<String> soundcloudLinks, String photos, String tags, Integer userId) {
		urlAggregator.addVideoLinks(project, videoLinks);
		urlAggregator.addSoundcloudLinks(project, soundcloudLinks);
		List<String> photoUrls = stringSplitter.split(photos);
		beanAggregator.addProjectPhoto(project, photoUrls);
		tagAggregator.addProjectTags(project, tags);
		projectService.saveProject(project, userId);
	}

}
