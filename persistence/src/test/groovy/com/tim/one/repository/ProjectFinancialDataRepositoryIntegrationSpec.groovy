package com.tim.one.repository;

import static org.junit.Assert.assertFalse

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional

import spock.lang.Specification

import com.tim.one.model.ProjectBusinessCase;
import com.tim.one.model.ProjectFinancialData
import com.tim.one.model.ProjectUnitSale
import com.tim.one.model.ProjectVariableCost

@ContextConfiguration(locations=["classpath:/persistence-appctx.xml"])
@Transactional
public class ProjectFinancialDataRepositoryIntegrationSpec extends Specification {

  @Autowired
  ProjectFinancialDataRepository repository

  def "should find project financial data by id"() {
    given: "A Project financial data"
      ProjectFinancialData projectFinancialData = new ProjectFinancialData(); 
    and: "Saving a project financial data"
      repository.save(projectFinancialData)
    when: "We find a project financial data by id"
      ProjectFinancialData result = repository.findOne(projectFinancialData.id)
    then: "We expect to find it"
      result.getId() == projectFinancialData.getId()
    cleanup:"We delete project rate"
      repository.delete(projectFinancialData)
  }
  
  def "should find project financial data by account"() {
    given: "A Project financial data"
      ProjectFinancialData projectFinancialData = new ProjectFinancialData()
    and: "Assigning account to projectFinancialData"
      String account = "account"
      projectFinancialData.account = account
    and: "Saving a project financial data"
      repository.save(projectFinancialData)
    when: "We find a project financial data by account"
      ProjectFinancialData result = repository.findProjectFinancialDataByAccount(account)
    then: "We expect to find it"
      result.id == projectFinancialData.id
    cleanup:"We delete project rate"
      repository.delete(projectFinancialData)
  }
  
  def "should save a project financial data w/unitsale"() {
    given: "A project financial data"
      ProjectFinancialData projectFinancialData = new ProjectFinancialData()
    and: "A project unit sale"
      ProjectUnitSale projectUnitSale = new ProjectUnitSale()
    and: "Assigning unit sale to project"
      projectUnitSale.unitSale = new BigDecimal("250")
      def unitSales = [projectUnitSale] as Set
      projectFinancialData.projectUnitSales = unitSales
      and: "Saving project"
      repository.save(projectFinancialData)
    when: "we save project"
      def result = repository.findOne(projectFinancialData.id)
    then: "We get id"
      result.projectUnitSales.size() == 1
      result.projectUnitSales[0].unitSale == 250
    cleanup:"We delete project rate"
      repository.delete(projectFinancialData)
  }
  
  def "should save a project financial data w/variable cost"() {
    given: "A project financial data"
      ProjectFinancialData projectFinancialData = new ProjectFinancialData()
    and: "A project variable cost"
      ProjectVariableCost cost = new ProjectVariableCost()
    and: "Assigning variable cost to project"
      cost.value = new BigDecimal("250")
      def costs = [cost] as Set
      projectFinancialData.variableCosts = costs
      and: "Saving project"
      repository.save(projectFinancialData)
    when: "we save project"
      def result = repository.findOne(projectFinancialData.id)
    then: "We get id"
      result.variableCosts.size() == 1
      result.variableCosts[0].value == 250
    cleanup:"We delete project rate"
      repository.delete(projectFinancialData)
  }
  
  def "should save a project financial data w/businessCase"() {
    given: "A project financial data"
      ProjectFinancialData projectFinancialData = new ProjectFinancialData()
      def name = "BusinessCase"
    and: "A project business case"
      ProjectBusinessCase businessCase = new ProjectBusinessCase()
    and: "Assigning business case to project"
      businessCase.name = name
      projectFinancialData.projectBusinessCase = businessCase
      and: "Saving project"
      repository.save(projectFinancialData)
    when: "we save project"
      def result = repository.findOne(projectFinancialData.id)
    then: "We get id"
      result.projectBusinessCase != null
      result.projectBusinessCase.name == name 
    cleanup:"We delete project rate"
      repository.delete(projectFinancialData)
  }
  
}
