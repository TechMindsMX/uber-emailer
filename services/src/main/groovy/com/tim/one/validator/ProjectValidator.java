package com.tim.one.validator;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.tim.one.model.ProjectProvider;

@Service
public class ProjectValidator {

	public boolean valid(Set<ProjectProvider> projectProviders) {
		for (ProjectProvider projectProvider : projectProviders) {
			if (projectProvider.getProviderId() == null){
				return false;
			}
		}
		return true;
	}
	
}
