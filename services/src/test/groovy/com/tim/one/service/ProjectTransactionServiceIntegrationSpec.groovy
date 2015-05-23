package com.tim.one.service;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional

import spock.lang.Specification
import spock.lang.Unroll

import com.tim.one.bean.TransactionType
import com.tim.one.model.BulkUnitTx
import com.tim.one.model.Project
import com.tim.one.model.ProjectFinancialData
import com.tim.one.model.ProjectUnitSale
import com.tim.one.model.ProjectVariableCost
import com.tim.one.model.UnitTx
import com.tim.one.repository.BulkUnitTxRepository
import com.tim.one.repository.ProjectFinancialDataRepository
import com.tim.one.repository.ProjectRepository
import com.tim.one.repository.ProjectUnitSaleRepository
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
public class ProjectTransactionServiceIntegrationSpec extends Specification {
  
  @Autowired
  ProjectTransactionService projectTransactionService
  @Autowired
  ProjectVariableCostRepository projectVariableCostRepository
  @Autowired
  BulkUnitTxRepository bulkUnitTxRepository
  @Autowired
  ProjectUnitSaleRepository projectUnitSaleRepository
  @Autowired
  ProjectFinancialDataRepository projectFinancialDataRepository
  @Autowired
  ProjectService projectService;
  @Autowired
  ProjectRepository projectRepository

  @Unroll
  def """When we have a costs with #cost1Value% and #cost2Value% and a project budget #budget, we expect that project has a CRE #expectedCre"""() {
    given: "A project financial data"
    ProjectFinancialData projectFinancialData = new ProjectFinancialData()
    projectFinancialData.budget = budget
    and: "Two project variable cost"
    ProjectVariableCost cost1 = new ProjectVariableCost()
    ProjectVariableCost cost2 = new ProjectVariableCost()
    cost1.value = cost1Value
    cost2.value = cost2Value
    def variableCosts = [cost1, cost2] as Set
    and: "Saving costs"
    projectFinancialData.variableCosts = variableCosts
    projectFinancialDataRepository.save(projectFinancialData)
    when: "We transfer funds from trama to user"
    def cre = projectTransactionService.getCRE(projectFinancialData)
    then:"We expect the differences"
    cre == expectedCre
    cleanup:"We delete project's data"
    projectRepository.delete(projectFinancialData)
    where: "We have the next cases"
    cost1Value  | cost2Value | budget  || expectedCre
    5           | 10         | 600     || 615
    12          | 16         | 10100   || 10128
  }
  
  @Unroll
  def """When we have a costs with #cost1Value% and #cost2Value% and units with quantities #quantity1, #quantity2, #quantity3 and budget with #budget, we expect that project has a TRI #expectedTri"""() {
    given: "A project financial data"
    Project project = new Project()
    ProjectFinancialData projectFinancialData = new ProjectFinancialData()
    ProjectUnitSale projectUnitSale = new ProjectUnitSale()
    projectUnitSale.unitSale = new BigDecimal(250)
    projectFinancialData.budget = budget
    def unitSales = [projectUnitSale] as Set
    projectFinancialData.projectUnitSales = unitSales 
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
   and: "Three project units"
    UnitTx unit1 = new UnitTx()
    UnitTx unit2 = new UnitTx()
    UnitTx unit3 = new UnitTx()
    unit1.projectUnitSaleId = projectUnitSale.id
    unit2.projectUnitSaleId = projectUnitSale.id
    unit3.projectUnitSaleId = projectUnitSale.id
    unit1.quantity = quantity1
    unit2.quantity = quantity2
    unit3.quantity = quantity3
    unit1.type = TransactionType.FUNDING
    unit2.type = TransactionType.INVESTMENT
    unit3.type = TransactionType.CONSUMING
    and: "Saving units"
    BulkUnitTx bulk = new BulkUnitTx()
    def units = [unit1, unit2, unit3] as Set
    bulk.units = units
    bulkUnitTxRepository.save(bulk)
    when: "We transfer funds from trama to user"
    def tri = projectTransactionService.getTRI(projectFinancialData)
    then:"We expect the differences"
    tri == expectedTri
    cleanup:"We delete project's data"
    bulkUnitTxRepository.delete(bulk)
    projectRepository.delete(project)
    where: "We have the next cases"
    cost1Value  | cost2Value | quantity1  | quantity2 | quantity3 |  budget || expectedTri
    5           | 10         | 1          |     3     |     5     |  500    || 0.6277777778
    12          | 16         | 2          |     4     |     6     |  1000   || 0.3866666667
  }
  
  @Unroll
  def """When we have a costs with #cost1Value% and #cost2Value% and units with quantities #quantity1, #quantity2, #quantity3 and budget with #budget, we expect that project has a TRF #expectedTrf"""() {
given: "A project financial data"
    Project project = new Project()
    ProjectFinancialData projectFinancialData = new ProjectFinancialData()
    ProjectUnitSale projectUnitSale = new ProjectUnitSale()
    projectUnitSale.unitSale = new BigDecimal(250)
    projectFinancialData.budget = budget
    def unitSales = [projectUnitSale] as Set
    projectFinancialData.projectUnitSales = unitSales 
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
   and: "Three project units"
    UnitTx unit1 = new UnitTx()
    UnitTx unit2 = new UnitTx()
    UnitTx unit3 = new UnitTx()
    unit1.projectUnitSaleId = projectUnitSale.id
    unit2.projectUnitSaleId = projectUnitSale.id
    unit3.projectUnitSaleId = projectUnitSale.id
    unit1.quantity = quantity1
    unit2.quantity = quantity2
    unit3.quantity = quantity3
    unit1.type = TransactionType.FUNDING
    unit2.type = TransactionType.INVESTMENT
    unit3.type = TransactionType.CONSUMING
    and: "Saving units"
    BulkUnitTx bulk = new BulkUnitTx()
    def units = [unit1, unit2, unit3] as Set
    bulk.units = units
    bulkUnitTxRepository.save(bulk)
    when: "We transfer funds from trama to user"
    def trf = projectTransactionService.getTRF(projectFinancialData)
    then:"We expect the differences"
    trf == expectedTrf
    cleanup:"We delete project's data"
    bulkUnitTxRepository.delete(bulk)
    projectRepository.delete(project)
    where: "We have the next cases"
    cost1Value  | cost2Value | quantity1  | quantity2 | quantity3 |  budget || expectedTrf
    5           | 10         | 1          |     3     |     5     |  500    || 3.1388888890
    12          | 16         | 2          |     4     |     6     |  1000   || 1.1600000001
  }
  
}
