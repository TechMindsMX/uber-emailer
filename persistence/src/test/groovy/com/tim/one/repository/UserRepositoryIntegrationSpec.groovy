package com.tim.one.repository;

import static org.junit.Assert.assertFalse

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional;

import spock.lang.Specification

import com.tim.one.model.User
import com.tim.one.model.UserAccount

@ContextConfiguration(locations=["classpath:/persistence-appctx.xml"])
@Transactional
public class UserRepositoryIntegrationSpec extends Specification {

  @Autowired
  UserRepository repository

  def "should find user by email"() {
    given: "A User"
      User user = new User(); 
    and: "Assigning email to user"
      String email = "josdem@trama.mx"
      user.setEmail(email)
    and: "Saving an user"
      repository.save(user)
    when: "We find an user by email"
      User result = repository.findUserByEmail(email)
    then: "We expect to find it"
      result.getEmail() == user.getEmail() 
    cleanup:"We delete project rate"
      repository.delete(user)
  }
  
  def "should find user w/account"() {
    given: "A User"
      User user = new User();
      UserAccount userAccount = new UserAccount()
    and: "Assigning userAccount to user"
      user.userAccount = userAccount
    and: "Saving an user"
      repository.save(user)
    when: "We find an user by email"
      User result = repository.findOne(user.id)
    then: "We expect to find it"
      result.userAccount == userAccount
    cleanup:"We delete project rate"
      repository.delete(user)
  }
  
  def "should find user w/clabe"() {
    given: "A User"
      User user = new User();
      UserAccount userAccount = new UserAccount()
    and: "Assigning clabe to userAccount"
      def clabe = "clabe"
      userAccount.stpClabe = clabe
    and: "Assigning userAccount to user"
      user.userAccount = userAccount
      userAccount.user = user
    and: "Saving an user"
      repository.save(user)
    when: "We find an user by email"
      User result = repository.findUserByClabe(clabe)
    then: "We expect to find it"
      result.userAccount.stpClabe == clabe
    cleanup:"We delete project rate"
      repository.delete(user)
  }
	
	def "should find a user from two by clabe"() {
		given: "Two User"
			User user1 = new User();
			User user2 = new User();
			UserAccount userAccount1 = new UserAccount()
			UserAccount userAccount2 = new UserAccount()
		and: "Assigning clabe to userAccount"
			def clabe1 = "clabe"
			def clabe2 = "clabe1"
			userAccount1.stpClabe = clabe1
			userAccount2.stpClabe = clabe2
		and: "Assigning userAccount to user"
			user1.userAccount = userAccount1
			user2.userAccount = userAccount2
			userAccount1.user = user1
			userAccount2.user = user2
		and: "Saving an user"
			repository.save(user1)
			repository.save(user2)
		when: "We find an user by email"
			User result = repository.findUserByClabe(clabe1)
		then: "We expect to find it"
			result.userAccount.stpClabe == clabe1
		cleanup:"We delete project rate"
			repository.delete(user1)
			repository.delete(user2)
	}
  
}
