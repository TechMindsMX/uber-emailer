package com.tim.one.service;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

import spock.lang.Specification
import spock.lang.Unroll

import com.tim.one.bean.AdminAccountType
import com.tim.one.bean.TransactionType
import com.tim.one.model.Project
import com.tim.one.model.ProjectFinancialData
import com.tim.one.model.TramaAccount
import com.tim.one.model.User
import com.tim.one.repository.ProjectRepository
import com.tim.one.repository.ProjectTxRepository;
import com.tim.one.repository.TramaAccountRepository
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
public class UserTransactionServiceIntegrationSpec extends Specification {
  
  @Autowired
  UserRepository userRepository
  @Autowired
  ProjectRepository projectRepository
  @Autowired
  UserTransactionService userTransactionService
  @Autowired
  TramaAccountRepository tramaAccountRepository

  @Unroll
  def """When we have user balance #userBalance and trama balance #tramaBalance and we wanted to measure return of investment with amount #amount we expect user balance #expectedUserBalance and trama balance #expectedTramaBalance"""() {
    given: "A user"
    User user = new User()
    user.balance = userBalance
    and: "A project"
    def project = new Project()
		def projectFinancialData = new ProjectFinancialData()
		projectFinancialData.balance = projectBalance
		projectFinancialData.projectUnitSales = [] as Set
		project.projectFinancialData = projectFinancialData
		projectFinancialData.project = project
    and: "Trama account"
    TramaAccount tramaAccount = tramaAccountRepository.findByType(AdminAccountType.TRAMA)
    tramaAccount.balance = tramaBalance
    and: "Persisting user, project and tramaAccount"
    userRepository.save(user)
    projectRepository.save(project)
    tramaAccountRepository.save(tramaAccount)
    when: "We transfer funds to user"
    userTransactionService.measureReturnOfInvestment(user.id, projectFinancialData, amount, TransactionType.FUNDING)
    then:"We expect the differences"
    expectedUserBalance == userRepository.findOne(user.id).balance
    expectedTramaBalance == tramaAccountRepository.findByType(AdminAccountType.TRAMA).balance
    cleanup:"We delete project's data"
    userRepository.delete(user)
    projectRepository.delete(project)
    where:"We have the next cases"
    projectBalance | userBalance  | tramaBalance  | amount || expectedProjectBalance | expectedUserBalance | expectedTramaBalance
    10 					   | 100          |1000           | 10     || 0.00                   | 109.00              | 1001.00
    1300 					 | 2000         |4000           | 1300   || 0.00                   | 3170.00             | 4130.00
  }
  
}
