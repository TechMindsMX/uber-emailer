package com.tim.one.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

import spock.lang.Specification

import com.tim.one.exception.BusinessException
import com.tim.one.model.Project
import com.tim.one.model.ProjectFinancialData
import com.tim.one.model.User
import com.tim.one.repository.ProjectRepository
import com.tim.one.repository.UserRepository

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
public class TransactionApplierServiceIntegrationSpec extends Specification {
  
  @Autowired
  UserRepository userRepository
  @Autowired
  ProjectRepository projectRepository
  @Autowired
  TransactionApplier transactionApplier

  def "should add amount to user"() {
    given: "A user"
    User user = new User()
    user.balance = 500
    and: "Persisting user"
    userRepository.save(user)
    when: "We transfer funds to user"
    def amount = 100
    transactionApplier.addAmount(user, amount)
    then:"We expect the differences"
    def expectedUser = userRepository.findOne(user.id)
    expectedUser.balance == 600
    cleanup:"We delete project's data"
    userRepository.delete(user)
  }
  
  def "should substract amount to user"() {
    given: "A user"
    User user = new User()
    user.balance = 500
    and: "Persisting user"
    userRepository.save(user)
    when: "We get funds from user"
    def amount = 100
    transactionApplier.substractAmount(user, amount)
    then:"We expect the differences"
    def expectedUser = userRepository.findOne(user.id)
    expectedUser.balance == 400
    cleanup:"We delete project's data"
    userRepository.delete(user)
  }
  
  def "should add amount to project"() {
    given: "A project"
    Project project = new Project()
    ProjectFinancialData projectFinancialData = new ProjectFinancialData()
    projectFinancialData.balance = 500
    and: "Persisting project"
    project.projectFinancialData = projectFinancialData
    projectRepository.save(project)
    when: "We transfer funds to project"
    def amount = 100
    transactionApplier.addAmount(projectFinancialData, amount)
    then:"We expect the differences"
    def expectedProject = projectRepository.findOne(project.id)
    expectedProject.projectFinancialData.balance == 600
    cleanup:"We delete project's data"
    projectRepository.delete(project)
  }
  
  def "should not add amount to user due to invalid amount"() {
    given: "A user"
    User user = new User()
    user.balance = 500
    and: "Persisting user"
    userRepository.save(user)
    when: "We transfer funds to user"
    def amount = -100
    transactionApplier.addAmount(user, amount)
    then:"We expect the differences"
    thrown BusinessException
    cleanup:"We delete project's data"
    userRepository.delete(user)
  }
  
  def "should substract amount to project"() {
     given: "A project"
    Project project = new Project()
    ProjectFinancialData projectFinancialData = new ProjectFinancialData()
    projectFinancialData.balance = 500
    and: "Persisting project"
    project.projectFinancialData = projectFinancialData
    projectRepository.save(project)
    when: "We get funds from project"
    def amount = 100
    transactionApplier.substractAmount(projectFinancialData, amount)
    then:"We expect the differences"
    def expectedProject = projectRepository.findOne(project.id)
    expectedProject.projectFinancialData.balance == 400
    cleanup:"We delete project's data"
    projectRepository.delete(project)
  }
  
}
