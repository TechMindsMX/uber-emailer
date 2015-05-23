package com.tim.one.collaborator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tim.one.helper.ProjectHelper;
import com.tim.one.model.Project;
import com.tim.one.model.ProjectTag;

/**
 * @author josdem
 * @understands A class who helps to set tags to the project
 * 
 */

@Component
public class TagAggregator {

	@Autowired
	private TagSplitter tagCollaborator;
	@Autowired
	private ProjectHelper projectHelper;

	public void addProjectTags(Project project, String tags) {
		Set<ProjectTag> tagList = new HashSet<ProjectTag>();
		List<String> strings = tagCollaborator.split(tags);
		for (String string : strings) {
			if(!StringUtils.isEmpty(string)){
				ProjectTag projectTag =  projectHelper.createProjectTag();
				projectTag.setTag(string);
				projectTag.setProject(project);
				tagList.add(projectTag);
			}
		}
		project.setTags(tagList);
	}
	
}
