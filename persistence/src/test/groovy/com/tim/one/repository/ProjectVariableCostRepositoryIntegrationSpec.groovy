package com.tim.one.repository;

import static org.junit.Assert.assertFalse

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional;

import spock.lang.Specification

import com.tim.one.model.ProjectFinancialData
import com.tim.one.model.ProjectVariableCost

@ContextConfiguration(locations=["classpath:/persistence-appctx.xml"])
@Transactional
public class ProjectVariableCostRepositoryIntegrationSpec extends Specification {

  @Autowired
  ProjectVariableCostRepository repository

  def "should find project variable cost by project"() {
    given: "A Project variable cost"
      ProjectFinancialData projectFinancialData = new ProjectFinancialData()
      ProjectVariableCost projectVariableCost = new ProjectVariableCost()
    and: "Assigning project to projectVariableCost"
      projectFinancialData.variableCosts = [projectVariableCost] as Set
      projectVariableCost.projectFinancialData = projectFinancialData
    and: "Saving a project variable cost"
      repository.save(projectFinancialData)
    when: "We find a project variable cost by project"
      List<ProjectVariableCost> projects = repository.findByProjectFinancialData(projectFinancialData)
    then: "We expect to find it"
      projects.size() == 1
      projects[0] == projectVariableCost
    cleanup:"We delete project rate"
      repository.delete(projectFinancialData)
  }
  
}
