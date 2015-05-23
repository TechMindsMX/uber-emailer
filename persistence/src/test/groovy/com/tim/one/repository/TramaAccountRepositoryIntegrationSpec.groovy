package com.tim.one.repository;

import static org.junit.Assert.assertFalse

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

import spock.lang.Specification

import com.tim.one.model.SessionKey
import com.tim.one.model.TramaAccount;
import com.tim.one.bean.AdminAccountType;


@ContextConfiguration(locations=["classpath:/persistence-appctx.xml"])
public class TramaAccountRepositoryIntegrationSpec extends Specification {

  @Autowired
  TramaAccountRepository repository

  def "should find a trama account by type"() {
    given: "A trama type"
      AdminAccountType type = AdminAccountType.TRAMA
    when: "We find a trama account by type"
      TramaAccount result = repository.findByType(type)
    then: "We expect to find it"
      result.type == type 
  }
  
}
