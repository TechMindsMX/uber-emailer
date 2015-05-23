package com.tim.one.service;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

import spock.lang.Specification

import com.tim.one.bean.ResultType
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
public class STPAccountServiceIntegrationSpec extends Specification {
  
  @Autowired
  UserRepository userRepository
  @Autowired
  private UserAccountRepository userAccountRepository;
  @Autowired
  STPAccountService stpAccountService

  def "should create an userAccount by user"() {
    given: "A user"
    User user = new User()
    def bankCode = 1
    def clabe = "012180028515762163"
    and: "Persisting user"
    userRepository.save(user)
    when: "We transfer funds from trama to user"
    def result = stpAccountService.createAccout(user.id, bankCode, clabe)
    def userFromRepository = userRepository.findOne(user.id)
    then:"We expect the differences"
    result == ResultType.SUCCESS
    userFromRepository.userAccount != null
  }
  
  def "should get an exception when create an userAccount by user"() {
    given: "A user"
    User user = new User()
    def bankCode = 1
    def clabe = "clabe"
    and: "Persisting user"
    userRepository.save(user)
    when: "We transfer funds from trama to user"
    def result = stpAccountService.createAccout(user.id, bankCode, clabe)
    then:"We expect the differences"
    thrown BusinessException
  }
  
  def cleanup(){
    userAccountRepository.deleteAll()
    userRepository.deleteAll()
  }
  
}
