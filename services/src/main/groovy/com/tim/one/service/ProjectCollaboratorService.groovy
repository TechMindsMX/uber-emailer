package com.tim.one.service

import com.tim.one.model.Project
import com.tim.one.model.ProjectFinancialData

interface ProjectCollaboratorService {
	
	public Set<Project> getProjectsFundedOrInvestedByUserId(Integer userId)
	public Integer getTotalUnits(Integer projectUnitSaleId)
	public ProjectFinancialData getProjectFinancialData(Project project)

}
