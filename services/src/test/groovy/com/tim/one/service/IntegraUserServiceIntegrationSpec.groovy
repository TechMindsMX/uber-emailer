package com.tim.one.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional

import spock.lang.Specification

import com.tim.one.exception.BusinessException
import com.tim.one.model.IntegraUser
import com.tim.one.repository.IntegraUserRepository

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

@Transactional
public class IntegraUserServiceIntegrationSpec extends Specification {

	@Autowired
	IntegraUserService userService
	@Autowired
	IntegraUserRepository userRepository

	def "Should create user"() {
		given: "An integra uuid"
		def integraUuid = "integraUuid"
		and: "name and email"
		def name = "name"
		def email = "josdem@gmail.com"
		when: "We create user"
		def user = userService.create(integraUuid, name, email)
		then:"We expect project with collections"
		user.integraUuid == integraUuid
		user.timoneUuid.length() == 32
		user.stpClabe.length() == 18
		user.balance == new BigDecimal(0.00)
		cleanup:"We delete user"
		userRepository.delete(user)
	}
	
	def "Should find user by integra uuid"() {
		given: "An integra uuid"
		def timoneUuid = "timoneUuid"
		and: "Integra User"
		def user = new IntegraUser()
		user.timoneUuid = timoneUuid
		userRepository.save(user)
		when: "We find user"
		def result = userService.getByUuid(timoneUuid)
		then:"We expect project with collections"
		result.timoneUuid == timoneUuid
		userRepository.delete(user)
	}
	
	def "Should find an empty user when no integra uuid"() {
		given: "An integra uuid"
		def integraUuid = "integraUuid"
		when: "We find user"
		def result = userService.getByUuid(integraUuid)
		then:"We expect project with collections"
		result
	}
	
	def "Should return if no integra uuid"() {
		given: "An integra uuid"
		def integraUuid = null
		when: "We find user"
		def result = userService.getByUuid(integraUuid)
		then:"We expect project with collections"
		result.integraUuid == null
		result.timoneUuid == null
		result.stpClabe == null
		result.balance == new BigDecimal(0.00)
	}
	
}

