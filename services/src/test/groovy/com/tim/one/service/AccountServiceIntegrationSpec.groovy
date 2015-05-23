package com.tim.one.service;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

import spock.lang.Specification
import spock.lang.Unroll

import com.tim.one.bean.AdminAccountType
import com.tim.one.exception.BusinessException
import com.tim.one.model.TramaAccount
import com.tim.one.model.User
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
public class AccountServiceIntegrationSpec extends Specification {

  @Autowired
  AccountService accountService
  @Autowired
  UserRepository userRepository
  @Autowired
  TramaAccountRepository tramaAccountRepository

  @Unroll
  def """When we have an user with #initBalance and trama account with #tramaBalance and we transfer #amountToTransfer from trama to user, we expect that user has #expectedUserBalance and trama has #expectedTramaBalance"""() {
    given: "An user with valid account"
      User user = new User()
    def account = "U01"
      user.account = account
    and: "An amount in trama"
      TramaAccount tramaAccount = tramaAccountRepository.findByType(AdminAccountType.TRAMA)
      tramaAccount.balance = tramaBalance
      tramaAccountRepository.save(tramaAccount)
    and: "An amount"
      BigDecimal amount = amountToTransfer
      user.balance = initBalance
    and: "Saving user"
      userRepository.save(user)
    when: "We transfer funds from trama to user"
      accountService.transferFundsFromTramaTo(account, amount)
    then:"We expect the differences"
      expectedUserBalance == userRepository.findUserByAccount(account).getBalance()
      expectedTramaBalance == tramaAccountRepository.findByType(AdminAccountType.TRAMA).getBalance()
    cleanup:"We delete the user"
      userRepository.delete(user)
    where: "We have the next cases"
      initBalance  | amountToTransfer | tramaBalance || expectedUserBalance | expectedTramaBalance
      100          | 100              | 1000         || 200                 | 900
      0            | 25               | 50           || 25                  | 25
  }

  @Unroll
  def """When we have an user with #initBalance and trama account with #tramaBalance and we transfer #amountToTransfer from trama to user, we expect #expectedException"""() {
    given: "An user with valid account"
      User user = new User()
      def account = "U01"
      user.account = account
    and: "An amount in trama"
      TramaAccount tramaAccount = tramaAccountRepository.findByType(AdminAccountType.TRAMA)
      tramaAccount.balance = tramaBalance
      tramaAccountRepository.save(tramaAccount)
    and: "An amount"
      BigDecimal amount = amountToTransfer
      user.balance = initBalance
    and: "Saving user"
      userRepository.save(user)
    when: "We transfer funds from trama to user"
      accountService.transferFundsFromTramaTo(account, amount)
    then:"We expect the differences"
      thrown expectedException
    cleanup:"We delete the user"
      userRepository.delete(user);
    where: "We have the next cases"
      initBalance  | amountToTransfer | tramaBalance || expectedException
      100          | 100              | 0            || BusinessException
      100          | 0                | 500          || BusinessException
      100          | -100             | 500          || BusinessException
  }
}
