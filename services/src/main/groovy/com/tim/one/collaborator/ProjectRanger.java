package com.tim.one.collaborator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.tim.one.model.Project;

@Component
public class ProjectRanger {
	
	public Set<Project> distinct(Set<Project> byKeywordSet) {
		Set<Project> result = new HashSet<Project>();
		List<Integer> ids = new ArrayList<Integer>();
		for (Project project : byKeywordSet) {
			if (!ids.contains(project.getId())){
				ids.add(project.getId());
				result.add(project);
			}
		}
		return result;
	}

}
