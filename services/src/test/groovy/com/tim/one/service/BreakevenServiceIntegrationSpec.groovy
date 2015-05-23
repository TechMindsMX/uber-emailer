package com.tim.one.service;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional;

import spock.lang.Specification
import spock.lang.Unroll

import com.tim.one.model.Project
import com.tim.one.model.ProjectFinancialData
import com.tim.one.model.ProjectProvider
import com.tim.one.model.ProjectVariableCost
import com.tim.one.repository.ProjectRepository
import com.tim.one.repository.ProjectVariableCostRepository

@ContextConfiguration(locations=[
  "classpath:/services-appctx.xml",
  "classpath:/persistence-appctx.xml",
  "classpath:/jms-appctx.xml",
  "classpath:/mail-context.xml",
  "classpath:/transaction-appctx.xml",
  "classpath:/formatter-appctx.xml",
  "classpath:/stp-appctx.xml",
  "classpath:/aop-appctx.xml",
  "classpath:/properties-appctx.xml"])
@Transactional
public class BreakevenServiceIntegrationSpec extends Specification {

  @Autowired
  BreakevenService breakevenService
  @Autowired
  ProjectRepository projectRepository
  @Autowired
  ProjectVariableCostRepository projectVariableCostRepository

  @Unroll
  def """When we have a costs with #cost1Value% and #cost2Value% and a project budget #budget and a revenue potential #revenuePotential, we expect that project has a breakeven #expectedBreakeven"""() {
    given: "A project financial data"
      Project project = new Project()
      ProjectFinancialData projectFinancialData = new ProjectFinancialData()
      projectFinancialData.budget = budget
      projectFinancialData.revenuePotential = revenuePotential
    and: "Two project variable cost"
      ProjectVariableCost cost1 = new ProjectVariableCost()
      ProjectVariableCost cost2 = new ProjectVariableCost()
      cost1.value = cost1Value
      cost2.value = cost2Value
      def variableCosts = [cost1, cost2] as Set
    and: "Saving costs"
      projectFinancialData.variableCosts = variableCosts
      project.projectFinancialData = projectFinancialData
      projectRepository.save(project)
    when: "We transfer funds from trama to user"
      def breakeven = breakevenService.getCalculatedBreakeven(projectFinancialData)
    then:"We expect the differences"
      breakeven == expectedBreakeven
    cleanup:"We delete project's data"
      projectRepository.delete(project)
    where: "We have the next cases"
      cost1Value  | cost2Value | budget  | revenuePotential || expectedBreakeven
      6           | 8          | 500     | 250              || 581.3953488250
      10          | 15         | 10000   | 500              || 13333.3333333500
  }
  
  @Unroll
  def """When we have provider with #advanceQuantity advance and #settlementQuantity settlement, we expect that project has a tramaFee: #expectedTramaFee"""() {
    given: "A project provider"
      ProjectProvider provider = new ProjectProvider()
      provider.advanceQuantity = advanceQuantity
      provider.settlementQuantity = settlementQuantity
    and: "Adding provider to list"
      def providers = [provider] as Set
    when: "We transfer funds from trama to user"
      def tramaFee = breakevenService.getTramaFee(providers)
    then:"We expect the differences"
      tramaFee == expectedTramaFee
    where: "We have the next cases"
      advanceQuantity  | settlementQuantity || expectedTramaFee
      1000              | 500               || 75.0000000000
      500               | 250               || 37.5000000000
  }
  
}
