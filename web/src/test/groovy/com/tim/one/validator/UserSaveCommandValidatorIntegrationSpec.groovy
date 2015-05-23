package com.tim.one.validator

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional

import spock.lang.Specification
import spock.lang.Unroll

import com.tim.one.command.UserSaveCommand
import com.tim.one.integra.command.IntegraUserCommand

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
public class UserSaveCommandValidatorIntegrationSpec extends Specification {

	@Autowired
	CommandValidator validator

	@Unroll
	def """When we need to create an user with name #name and #email as email
  when we validate we expecting that result is #result"""() {
		given: "An integra user command"
		def command = new UserSaveCommand()
		when: "We assing values to user"
		command.name = name
		command.email = email
		then:"We expect the differences"
		result == validator.isValid(command)
		where:"We have the next cases"
		name 		 																 | email 						   || result 
		"josdem" 																 | "josdem@josdem.mx"  || true
		""  		 																 | "josdem@josdem.mx"  || false
		null  	 																 | "josdem@josdem.mx"  || false
		"josdem" 																 | ""  								 || false
		"josdem" 																 | null  							 || false
		"josdem" 																 | "josdem"  					 || false
		"<script>alert('hello josdem')</script>" | "josdem"  					 || false
		
	}
	
}

