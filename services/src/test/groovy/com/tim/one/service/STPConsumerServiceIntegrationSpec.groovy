package com.tim.one.service;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional;

import spock.lang.Specification

import com.tim.one.exception.BusinessException
import com.tim.one.model.User
import com.tim.one.repository.UserAccountRepository
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

public class STPConsumerServiceIntegrationSpec extends Specification {
  
  @Autowired
  UserRepository userRepository
  @Autowired
  UserAccountRepository userAccountRepository
  @Autowired
  STPConsumerService stpConsumerService
  @Autowired
  STPAccountService stpAccountService

  def "should create an userAccount by user"() {
    given: "A user"
    User user = new User()
    def bankCode = 40012
    def clabe = "012180028515762163"
    user.balance = 500
    user.name = "josdem"
    user.email = "joseluis.delacruz@trama.mx"
    and: "Persisting user"
    userRepository.save(user)
    and: "Creating account"
    stpAccountService.createAccout(user.id, bankCode, clabe)
    when: "We transfer funds from trama to user"
    def amount = 100
    def result = stpConsumerService.cashOut(user.id, amount);
    then:"We expect the differences"
    def expectedUser = userRepository.findOne(user.id)
    expectedUser.balance == 400
  }
  
  def "should not create an userAccount by user due to invalid bankCode"() {
    given: "A user"
    User user = new User()
    def bankCode = 1
    def clabe = "012180028515762163"
    user.balance = 500
    user.name = "josdem"
    user.email = "joseluis.delacruz@trama.mx"
    and: "Persisting user"
    userRepository.save(user)
    and: "Creating account"
    stpAccountService.createAccout(user.id, bankCode, clabe)
    when: "We transfer funds from trama to user"
    def amount = 100
    def result = stpConsumerService.cashOut(user.id, amount)
    then:"We expect the differences"
    thrown BusinessException
  }
  
  def "should do a cash in from STP"() {
    given: "A user"
    User user = new User()
    def bankCode = 40012
    def clabeOrdenante = "012180028515762163"
    user.balance = 500
    and: "Persisting user"
    userRepository.save(user)
    and: "Creating account"
    stpAccountService.createAccout(user.id, bankCode, clabeOrdenante)
		def savedUser = userRepository.findOne(user.id)
    when: "We transfer funds from trama to user"
    def amount = 100
    def reference = "12345678"
    def result = stpConsumerService.cashIn(null, clabeOrdenante, savedUser.userAccount.stpClabe, amount, reference, null, null);
    then:"We expect the differences"
		def expectedUser = userRepository.findOne(user.id)
    expectedUser.balance == 600
  }
  
  def cleanup(){
    userAccountRepository.deleteAll()
    userRepository.deleteAll()
  }
  
}
