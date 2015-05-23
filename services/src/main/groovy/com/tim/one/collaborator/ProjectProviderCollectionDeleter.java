package com.tim.one.collaborator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tim.one.model.Project;
import com.tim.one.model.ProjectProvider;
import com.tim.one.repository.ProjectProviderRepository;

@Component
public class ProjectProviderCollectionDeleter {
	
	@Autowired
	private ProjectProviderRepository projectProviderRepository;
	
	public void deleteProviders(Project project){
		List<ProjectProvider> providers = projectProviderRepository.findByProject(project);
		for (ProjectProvider projectProvider : providers) {
			projectProviderRepository.delete(projectProvider);
		}
	}
	
}
