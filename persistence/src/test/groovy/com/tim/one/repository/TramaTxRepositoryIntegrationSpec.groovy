package com.tim.one.repository;

import static org.junit.Assert.assertFalse

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

import spock.lang.Specification

import com.tim.one.bean.TramaAccountType;
import com.tim.one.model.TramaTx

@ContextConfiguration(locations=["classpath:/persistence-appctx.xml"])
public class TramaTxRepositoryIntegrationSpec extends Specification {

  @Autowired
  TramaTxRepository repository

  def "should find a trama tx by project"() {
    given: "A trama tx"
      TramaTx tramaTx = new TramaTx() 
    and: "Assigning an project and type to trama tx"
      Integer projectId = 101
      tramaTx.entityId = projectId
      tramaTx.type = TramaAccountType.PRODUCT
    and: "Saving trama tx"
      repository.save(tramaTx)
    when: "We find a trama tx by project"
      TramaTx result = repository.findByEntityIdAndType(projectId, TramaAccountType.PRODUCT)
    then: "We expect to find it"
      result.entityId == projectId 
    cleanup:"We delete trama tx"
      repository.delete(tramaTx)
  }
  
}
