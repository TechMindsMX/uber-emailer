package com.tim.one.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tim.one.bean.TransactionType;
import com.tim.one.collaborator.ProjectFilter;
import com.tim.one.collaborator.UnitCollaborator;
import com.tim.one.model.Project;
import com.tim.one.model.ProjectFinancialData;
import com.tim.one.model.UnitTx;
import com.tim.one.repository.ProjectFinancialDataRepository;
import com.tim.one.repository.ProjectRepository;
import com.tim.one.repository.ProjectVariableCostRepository;
import com.tim.one.service.CalculatorService;
import com.tim.one.service.ProjectTransactionService;
import com.tim.one.state.ApplicationState;

/**
 * @author josdem
 * @understands A class who knows how measure business quantities
 * 
 */

@Service
public class ProjectTransactionServiceImpl implements ProjectTransactionService {

	@Autowired
	private CalculatorService calculatorService;
	@Autowired
	private UnitCollaborator unitCollaborator;
	@Autowired
	private ProjectFilter projectFilter;
	@Autowired
	private ProjectVariableCostRepository projectVariableCostRepository;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private ProjectFinancialDataRepository projectFinancialDataRepository;
	@Autowired
  @Qualifier("properties")
  private Properties properties;
	
  public BigDecimal getTRI(ProjectFinancialData project) {
    return project != null ? calculatorService.getTRI(project) : new BigDecimal("0");
  }

  public BigDecimal getTRF(ProjectFinancialData project) {
    BigDecimal zero = new BigDecimal("0");
    if (project == null) return zero;
    BigDecimal tri = getTRI(project);
    BigDecimal ucn = calculatorService.getUnitsByType(project, TransactionType.CONSUMING);
    BigDecimal ref = tri.multiply(ucn);
    BigDecimal ucf = calculatorService.getUnitsByType(project, TransactionType.FUNDING);
    return ucf.equals(new BigDecimal("0")) ? zero : ref.divide(ucf, new Integer(properties.getProperty(ApplicationState.MATH_PRECISION)), BigDecimal.ROUND_HALF_UP);
  }

  public BigDecimal getMAC(Integer projectId) {
    ProjectFinancialData projectFinancialData = projectFinancialDataRepository.findOne(projectId);
    List<UnitTx> units = unitCollaborator.getUnitsByProjectAndType(projectFinancialData, TransactionType.ALL);
    return projectFinancialData.getBreakeven().subtract(unitCollaborator.getUnitsAmount(projectFinancialData, units));
  }

  public BigDecimal getCRE(ProjectFinancialData project) {
    return project != null ? calculatorService.getCRE(project) : new BigDecimal("0");
  }

  public List<Project> getMoreProfitable(Integer max) {
    Integer counter = 0;
    List<Project> projects = projectRepository.findProjectsByStatus(new Integer(properties
        .getProperty(ApplicationState.PRODUCTION_STATUS)));
    for (Project project : projects) {
      ProjectFinancialData projectFinancialData = projectFinancialDataRepository.findOne(project
          .getId());
      project.setTri(getTRI(projectFinancialData));
      if (counter > 0 && counter > max) {
        break;
      }
      counter++;
    }
    return projectFilter.filter(projects);
  }

}
