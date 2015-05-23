package com.tim.one.repository;

import static org.junit.Assert.assertFalse

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

import spock.lang.Specification

import com.tim.one.model.ProviderTx

@ContextConfiguration(locations=["classpath:/persistence-appctx.xml"])
public class ProviderTxRepositoryIntegrationSpec extends Specification {

  @Autowired
  ProviderTxRepository repository

  def "should find a user tx by user and dates"() {
    given: "A Provider tx"
      ProviderTx providerTx = new ProviderTx()
    and: "Assigning an user"
      Integer userId = 1
      providerTx.providerId = userId
    and: "Assigning a timestamp"
      Long timestamp = 1
      providerTx.timestamp = timestamp
    and: "Saving an user tx"
      repository.save(providerTx)
    when: "We find an user tx by userId and dates"
      def results = repository.findByUserAndDate(userId, 0L, 2L)
    then: "We expect to find it"
      results.size == 1
    cleanup:"We delete user id"
      repository.delete(providerTx)
  }
  
}
