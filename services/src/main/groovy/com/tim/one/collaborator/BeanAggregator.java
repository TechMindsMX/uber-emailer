package com.tim.one.collaborator;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tim.one.helper.ProjectHelper;
import com.tim.one.model.Project;
import com.tim.one.model.ProjectBusinessCase;
import com.tim.one.model.ProjectFinancialData;
import com.tim.one.model.ProjectPhoto;
import com.tim.one.repository.ProjectFinancialDataRepository;

/**
 * @author josdem
 * @understands A class who helps to set images names to the beans
 * 
 */

@Component
public class BeanAggregator {

	@Autowired
	private ProjectHelper projectHelper;
	@Autowired
	private ProjectBusinessCaseCollaborator projectBusinessCaseCollaborator;
	@Autowired
	private ProjectFinancialDataRepository projectFinancialDataRepository;

	public void addProjectBusinessCase(ProjectFinancialData projectFinancialData, Long name) {
		if (name != null) {
      ProjectBusinessCase projectBusinessCase = projectBusinessCaseCollaborator.getProjectBusinessCase(projectFinancialData);
      projectBusinessCase.setName(name.toString());
      projectFinancialData.setProjectBusinessCase(projectBusinessCase);
      projectBusinessCase.setProjectFinancialData(projectFinancialData);
    } 
	}

	public void addProjectPhoto(Project project, List<String> photoUrls) {
		Set<ProjectPhoto> filteredProjectPhotos = projectHelper.createProjectPhotos();
		for (String url : photoUrls) {
			ProjectPhoto projectPhoto = projectHelper.createProjectPhoto();
			projectPhoto.setUrl(url);
			projectPhoto.setProject(project);
			filteredProjectPhotos.add(projectPhoto);
		}
		project.setProjectPhotos(filteredProjectPhotos);
	}

}
