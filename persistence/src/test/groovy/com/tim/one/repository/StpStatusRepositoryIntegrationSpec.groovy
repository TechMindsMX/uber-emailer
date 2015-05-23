package com.tim.one.repository;

import static org.junit.Assert.assertFalse

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

import spock.lang.Specification

import com.tim.one.model.StpStatus

@ContextConfiguration(locations=["classpath:/persistence-appctx.xml"])
public class StpStatusRepositoryIntegrationSpec extends Specification {

  @Autowired
  StpStatusRepository repository

  def "should find a stp status by id"() {
    given: "A STP status"
      StpStatus stpStatus = new StpStatus();
    and: "Assigning an id"
      Integer id = 1
      stpStatus.id = id
    and: "Saving stp status"
      repository.save(stpStatus)
    when: "We find a stp status by id"
      StpStatus result = repository.findById(stpStatus.id)
    then: "We expect to find it"
      result.id == stpStatus.id 
    cleanup:"We delete bulk unit tx"
      repository.delete(stpStatus)
  }
  
}
