package com.tim.one.repository

import static org.junit.Assert.assertFalse

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional

import spock.lang.Specification

import com.tim.one.model.IntegraUser
import com.tim.one.repository.IntegraUserRepository;

@ContextConfiguration(locations=["classpath:/persistence-appctx.xml"])
@Transactional
public class IntegraUserRepositoryIntegrationSpec extends Specification {

  @Autowired
  IntegraUserRepository repository

  def "should find user by integra uuid"() {
    given: "A User"
      def user = new IntegraUser()
    and: "Assigning uuid to user"
      String uuid = "timoneUuid"
      user.timoneUuid = uuid
    and: "Saving an user"
      repository.save(user)
    when: "We find an user by uuid"
      IntegraUser result = repository.findByTimoneUuid(uuid)
    then: "We expect to find it"
      result.timoneUuid == uuid 
    cleanup:"We delete user"
      repository.delete(user)
  }

}
