package com.tim.one.repository;

import static org.junit.Assert.assertFalse

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

import spock.lang.Specification

import com.tim.one.model.SessionKey

@ContextConfiguration(locations=["classpath:/persistence-appctx.xml"])
public class SecurityRepositoryIntegrationSpec extends Specification {

  @Autowired
  SecurityRepository repository

  def "should find a session key by apiKey"() {
    given: "A session key"
      SessionKey sessionKey = new SessionKey(); 
    and: "Assigning an apiKey to sessionKey"
      String apiKey = "key"
      sessionKey.setApiKey(apiKey)
    and: "Saving an user"
      repository.save(sessionKey)
    when: "We find an user by email"
      SessionKey result = repository.findByApiKey(apiKey)
    then: "We expect to find it"
      result.apiKey == apiKey 
    cleanup:"We delete project rate"
      repository.delete(sessionKey)
  }
  
}
