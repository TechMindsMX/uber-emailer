package com.tim.one.repository;

import static org.junit.Assert.assertFalse

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

import spock.lang.Specification

import com.tim.one.model.UserTx

@ContextConfiguration(locations=["classpath:/persistence-appctx.xml"])
public class UserTxRepositoryIntegrationSpec extends Specification {

  @Autowired
  UserTxRepository repository

  def "should find a user tx by id"() {
    given: "A User tx"
      UserTx userTx = new UserTx() 
    and: "Saving an user tx"
      repository.save(userTx)
    when: "We find an user tx by id"
      UserTx result = repository.findById(userTx.id)
    then: "We expect to find it"
      result.id
    cleanup:"We delete user id"
      repository.delete(userTx)
  }
  
  def "should find a user tx by user and dates"() {
    given: "A User tx"
      UserTx userTx = new UserTx()
    and: "Assigning an user"
      Integer receiverId = 1
      userTx.receiverId = receiverId
    and: "Assigning a timestamp"
      Long timestamp = 1
      userTx.timestamp = timestamp
    and: "Saving an user tx"
      repository.save(userTx)
    when: "We find an user tx by userId and dates"
      def results = repository.findByUserAndDate(receiverId, 0L, 2L)
    then: "We expect to find it"
      results.size == 1
    cleanup:"We delete user id"
      repository.delete(userTx)
  }
	
	def "should not find a user tx by sender and dates"() {
		given: "A User tx"
			UserTx userTx = new UserTx()
		and: "Assigning an user"
			Integer senderId = 1
			userTx.senderId = senderId
		and: "Assigning a timestamp"
			Long timestamp = 1
			userTx.timestamp = timestamp
		and: "Saving an user tx"
			repository.save(userTx)
		when: "We find an user tx by userId and dates"
			def results = repository.findByUserAndDate(senderId, 0L, 2L)
		then: "We expect to find it"
			results.size == 1
		cleanup:"We delete user id"
			repository.delete(userTx)
	}
  
}
