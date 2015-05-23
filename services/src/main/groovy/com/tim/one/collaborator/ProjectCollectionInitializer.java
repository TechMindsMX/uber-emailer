package com.tim.one.collaborator;

import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.tim.one.model.BulkUnitTx;
import com.tim.one.model.Project;
import com.tim.one.model.ProjectFinancialData;
import com.tim.one.model.ProjectLog;
import com.tim.one.model.ProjectPhoto;
import com.tim.one.model.ProjectProvider;
import com.tim.one.model.ProjectSoundcloud;
import com.tim.one.model.ProjectTag;
import com.tim.one.model.ProjectUnitSale;
import com.tim.one.model.ProjectVariableCost;
import com.tim.one.model.ProjectVideo;
import com.tim.one.model.UnitTx;

@Component
public class ProjectCollectionInitializer {
	
	private Log log = LogFactory.getLog(getClass());

	public void initializeCollections(Project project) {
		if(project == null){
			return;
		}
		Set<ProjectVideo> projectVideos = project.getProjectVideos();
		for (ProjectVideo projectVideo : projectVideos) {
			log.debug(projectVideo);
		}
		Set<ProjectSoundcloud> projectSoundclouds = project.getProjectSoundclouds();
		for (ProjectSoundcloud projectSoundcloud : projectSoundclouds) {
			log.debug(projectSoundcloud);
		}
		Set<ProjectPhoto> projectPhotos = project.getProjectPhotos();
		for (ProjectPhoto projectPhoto : projectPhotos) {
			log.debug(projectPhoto);
		}
		Set<ProjectTag> tags = project.getTags();
		for (ProjectTag projectTag : tags) {
			log.debug(projectTag);
		}
		Set<ProjectLog> logs = project.getLogs();
		for (ProjectLog projectLog : logs) {
			log.debug(projectLog);
		}
		Set<ProjectProvider> providers = project.getProviders();
		for (ProjectProvider projectProvider : providers) {
			log.debug(projectProvider);
		}
		
		//Initializing projectFinancialData
		ProjectFinancialData projectFinancialData = project.getProjectFinancialData();
		if(projectFinancialData != null){
			Set<ProjectVariableCost> variableCosts = projectFinancialData.getVariableCosts();
			for (ProjectVariableCost projectVariableCost : variableCosts) {
				log.debug(projectVariableCost);
			}
			Set<ProjectUnitSale> unitSales = projectFinancialData.getProjectUnitSales();
			for (ProjectUnitSale projectUnitSale : unitSales) {
				log.debug(projectUnitSale);
			}
		}
	}

	public void initializeCollections(BulkUnitTx bulk) {
		for (UnitTx unit : bulk.getUnits()) {
			log.debug(unit);
		}
	}

}
