package com.tim.one.collaborator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tim.one.model.Project;

/**
 * @author josdem
 * @understands A class who helps to filter projects based in TRI
 *              Max value 10
 */

@Component
public class ProjectFilter {
	
	public List<Project> filter(List<Project> projects) {
		Collections.sort(projects);
		List<Project> filteredProjects = new ArrayList<Project>();
		for (Project project : projects) {
			filteredProjects.add(project);
			if (filteredProjects.size() == 10){
				break;
			}
		}
		return filteredProjects;
	}

}
