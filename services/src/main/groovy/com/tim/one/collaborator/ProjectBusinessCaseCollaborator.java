package com.tim.one.collaborator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tim.one.helper.ProjectHelper;
import com.tim.one.model.ProjectBusinessCase;
import com.tim.one.model.ProjectFinancialData;

@Component
public class ProjectBusinessCaseCollaborator {
	
	@Autowired
	private ProjectHelper projectHelper;

	public ProjectBusinessCase getProjectBusinessCase(ProjectFinancialData projectFinancialData) {
		return projectFinancialData.getProjectBusinessCase() == null ? projectHelper.createProjectBusinessCase() : projectFinancialData.getProjectBusinessCase();
	}

}
