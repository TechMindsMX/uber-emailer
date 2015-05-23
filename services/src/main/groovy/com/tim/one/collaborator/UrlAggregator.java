package com.tim.one.collaborator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tim.one.helper.ProjectHelper;
import com.tim.one.model.Project;
import com.tim.one.model.ProjectSoundcloud;
import com.tim.one.model.ProjectVideo;

/**
 * @author josdem
 * @understands A class who knows how to add stringLinks to the Project
 * 
 */

@Component
public class UrlAggregator {

	@Autowired
	private ProjectHelper projectHelper;
	
	public void addVideoLinks(Project project, List<String> youtubeLinks) {
		Set<ProjectVideo> projectYoutubes = new HashSet<ProjectVideo>();
		for (String string : youtubeLinks) {
			if (!StringUtils.isEmpty(string)){
				ProjectVideo projectYoutube = projectHelper.createProjectYoutube();
				projectYoutube.setUrl(string);
				projectYoutube.setProject(project);
				projectYoutubes.add(projectYoutube);
			}
		}
		project.setProjectVideos(projectYoutubes);
	}

	public void addSoundcloudLinks(Project project, List<String> soundcloudLinks) {
		Set<ProjectSoundcloud> projectSoundclouds = new HashSet<ProjectSoundcloud>();
		for (String string : soundcloudLinks) {
			if (!StringUtils.isEmpty(string)){
				ProjectSoundcloud projectSoundcloud = projectHelper.createProjectSoundcloud();
				projectSoundcloud.setUrl(string);
				projectSoundcloud.setProject(project);
				projectSoundclouds.add(projectSoundcloud);
			}
		}
		project.setProjectSoundclouds(projectSoundclouds);
	}
	
}
