package com.tim.one.repository;

import static org.junit.Assert.assertFalse

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

import spock.lang.Specification

import com.tim.one.model.SessionKey
import com.tim.one.model.TransferUserLimit;

@ContextConfiguration(locations=["classpath:/persistence-appctx.xml"])
public class TransferUserLimitRepositoryIntegrationSpec extends Specification {

  @Autowired
  TransferUserLimitRepository repository

  def "should find a transfer user limit by user"() {
    given: "A transfer user limit"
      TransferUserLimit transferUserLimit = new TransferUserLimit();
    and: "Assigning a userId"
      Integer userId = 1
      transferUserLimit.userId = userId
    and: "Saving a transfer user limit"
      repository.save(transferUserLimit)
    when: "We find a transfer user limit"
      List<TransferUserLimit> result = repository.findByUserId(userId)
    then: "We expect to find it"
      result.size == 1
    cleanup:"We delete the transfer user limit"
      repository.delete(transferUserLimit)
  }
  
  def "should find a transfer user limit by user and destination"() {
    given: "A transfer user limit"
      TransferUserLimit transferUserLimit = new TransferUserLimit(); 
    and: "Assigning a userId"
      Integer userId = 1
      transferUserLimit.userId = userId
    and: "Assigning a destinationId"
      Integer destinationId = 2
      transferUserLimit.destinationId = destinationId
    and: "Saving a transfer user limit"
      repository.save(transferUserLimit)
    when: "We find a transfer user limit"
      TransferUserLimit result = repository.findByUserIdAndDestinationId(userId, destinationId)
    then: "We expect to find it"
      result.id 
    cleanup:"We delete the transfer user limit"
      repository.delete(transferUserLimit)
  }
  
}
