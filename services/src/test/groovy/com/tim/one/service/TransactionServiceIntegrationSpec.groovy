package com.tim.one.service;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import spock.lang.*;

import com.tim.one.util.DateUtil;
import com.tim.one.model.TransferUserLimit;
import com.tim.one.model.User;
import com.tim.one.repository.TransferUserLimitRepository;
import com.tim.one.repository.UserRepository;
import com.tim.one.exception.*

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
public class TransactionServiceIntegrationSpec extends Specification {

  @Autowired
  TransactionService transactionService;
  @Autowired
  UserRepository userRepository;
  @Autowired
  TransferUserLimitRepository transferUserLimitRepository;
  @Autowired
  DateUtil dateUtil;
 
  @Unroll
  def """When we have a user with #initSenderBalance in account, and
  another user with #initReceiverBalance in account, when we transfer #amountToTransfer
  then we expect the first user had #expectedSenderBalance and the second #expectedReceiverBalance"""(){
    given: "Two existing users"
      User sender = new User();
      User receiver = new User();
    and: "A transfer limit"
      TransferUserLimit transferUserLimit = new TransferUserLimit();
    and: "We add balance to each user"
      BigDecimal amount = amountToTransfer
      sender.balance = initSenderBalance
      receiver.balance = initReceiverBalance 
      userRepository.save(sender);
      userRepository.save(receiver);
      transferUserLimit.setAmount(amount);
      transferUserLimit.setDestinationId(receiver.id);
      transferUserLimit.setUserId(sender.id);
      transferUserLimit.setTimestamp(dateUtil.createDateAsLong());
      userRepository.save(transferUserLimit);
    when:"We apply a transfer funds"
      transactionService.transferFunds(sender.id, receiver.id, amount);
    then:"We expect the differences"
      expectedSenderBalance == userRepository.findOne(sender.id).getBalance()
      expectedReceiverBalance == userRepository.findOne(receiver.id).getBalance()
    cleanup:"We delete the users"
      userRepository.delete(sender);
      userRepository.delete(receiver);
      transferUserLimitRepository.delete(transferUserLimit);
    where:"We have the next cases"
    initSenderBalance  | initReceiverBalance  | amountToTransfer || expectedSenderBalance | expectedReceiverBalance
    100                 |100                  | 10               || 90                    | 110
    2000                 |4000                  | 1300               || 700                    | 5300
  }
  
  @Unroll
  def "transfer funds error"(){
    given: "Two existing users"
      User sender = new User();
      User receiver = new User();
    and: "A transfer limit"
      TransferUserLimit transferUserLimit = new TransferUserLimit();
    and: "We add balance to each user"
      BigDecimal amount = amountToTransfer
      sender.balance = initSenderBalance
      receiver.balance = initReceiverBalance 
      userRepository.save(sender);
      userRepository.save(receiver);
      transferUserLimit.setAmount(amount);
      transferUserLimit.setDestinationId(receiver.id);
      transferUserLimit.setUserId(sender.id);
      transferUserLimit.setTimestamp(dateUtil.createDateAsLong());
      userRepository.save(transferUserLimit);
    when:"We apply a transfer funds"
      transactionService.transferFunds(sender.id, receiver.id, amount);
    then:"We expect the differences"
      thrown expectedException
    cleanup:"We delete the users"
      userRepository.delete(sender);
      userRepository.delete(receiver);
      transferUserLimitRepository.delete(transferUserLimit);
    where:"We have the next cases"
    initSenderBalance  | initReceiverBalance  | amountToTransfer || expectedException
    0                 |100                  | 10               || BusinessException
    0                 |4000                  | 1300           || BusinessException
  }
}
