package com.tim.one.collaborator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tim.one.model.ProjectFinancialData;
import com.tim.one.model.ProjectVariableCost;
import com.tim.one.repository.ProjectFinancialDataRepository;
import com.tim.one.repository.ProjectVariableCostRepository;

@Component
public class ProjectVariableCostCollaborator {
  
  @Autowired
  private ProjectVariableCostRepository projectVariableCostRepository;
  @Autowired
  private ProjectFinancialDataRepository projectFinancialDataRepository;
  
  public void deleteProjectVariableCosts(ProjectFinancialData project) {
    List<ProjectVariableCost> costs = projectVariableCostRepository.findByProjectFinancialData(project);
    for (ProjectVariableCost projectVariableCost : costs) {
      projectVariableCostRepository.delete(projectVariableCost);
    }
  }

}
