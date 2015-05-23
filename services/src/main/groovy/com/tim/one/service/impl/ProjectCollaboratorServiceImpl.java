package com.tim.one.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim.one.collaborator.ProjectCollectionInitializer;
import com.tim.one.helper.ProjectHelper;
import com.tim.one.model.Project;
import com.tim.one.model.ProjectFinancialData;
import com.tim.one.model.ProjectUnitSale;
import com.tim.one.model.UnitTx;
import com.tim.one.repository.ProjectRepository;
import com.tim.one.repository.ProjectUnitSaleRepository;
import com.tim.one.repository.UnitTxRepository;
import com.tim.one.service.ProjectCollaboratorService;

@Service
public class ProjectCollaboratorServiceImpl implements ProjectCollaboratorService {
	
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
  private ProjectUnitSaleRepository projectUnitSaleRepository;
	@Autowired
	private UnitTxRepository unitTxRepository;
	@Autowired
	private ProjectHelper projectHelper;
	@Autowired
	private ProjectCollectionInitializer projectCollectionInitializer;
	
	public Set<Project> getProjectsFundedOrInvestedByUserId(Integer userId) {
		Set<Project> projects = new HashSet<Project>();
		List<UnitTx> units = unitTxRepository.findUnitsByUserId(userId);
		for (UnitTx unitTx : units) {
			ProjectUnitSale projectUnitSale = projectUnitSaleRepository.findOne(unitTx.getProjectUnitSaleId());
			Project project = projectUnitSale.getProjectFinancialData().getProject();
			projects.add(project);
		}
		for (Project project : projects) {
			projectCollectionInitializer.initializeCollections(project);
		}
		return projects;
	}

	public Integer getTotalUnits(Integer projectUnitSaleId) {
		List<UnitTx> units = unitTxRepository.findUnitsByProjectUnitSaleId(projectUnitSaleId);
		Integer total = 0;
		for (UnitTx unitTx : units) {
			total = total + unitTx.getQuantity();
		}
		return total; 
	}

	public ProjectFinancialData getProjectFinancialData(Project project) {
		if(project.getProjectFinancialData() == null){
			ProjectFinancialData projectFinancialData = projectHelper.createProjectFinancialData();
			project.setProjectFinancialData(projectFinancialData);
			projectFinancialData.setProject(project);
		}
		return project.getProjectFinancialData();
	}

}
