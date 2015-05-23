package com.tim.one.collaborator;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.collaborator.ProjectVariableCostCollaborator;
import com.tim.one.model.ProjectFinancialData;
import com.tim.one.model.ProjectVariableCost;
import com.tim.one.repository.ProjectFinancialDataRepository;
import com.tim.one.repository.ProjectVariableCostRepository;

public class TestProjectVariableCostCollaborator {
  
  @InjectMocks
  private ProjectVariableCostCollaborator collaborator = new ProjectVariableCostCollaborator();
  
  @Mock
  private ProjectVariableCostRepository projectVariableCostRepository;
  @Mock
  private ProjectFinancialDataRepository projectFinancialDataRepository;
  @Mock
  private ProjectVariableCost projectVariableCost;
  @Mock
  private ProjectFinancialData project;

  private Integer projectId = 1;
  private List<ProjectVariableCost> costs = new ArrayList<ProjectVariableCost>();

  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
  }
  
  @Test
  public void shouldDeleteProjectVariableCosts() throws Exception {
    costs.add(projectVariableCost);
    when(projectFinancialDataRepository.findOne(projectId)).thenReturn(project);
    when(projectVariableCostRepository.findByProjectFinancialData(project)).thenReturn(costs);
    
    collaborator.deleteProjectVariableCosts(project);
    
    verify(projectVariableCostRepository).delete(projectVariableCost);
  }

}
