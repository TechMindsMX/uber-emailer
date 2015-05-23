package com.tim.one.validator

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional

import spock.lang.Specification
import spock.lang.Unroll

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
public class IntegraUserCommandValidatorIntegrationSpec extends Specification {

	@Autowired
	CommandValidator validator

	@Unroll
	def """When we have a user with #uuid as uuid, and #name as name and #email as email
  when we validate we expecting that result is #result"""() {
		given: "An integra user command"
		def userCommand = new IntegraUserCommand()
		when: "We assing values to user"
		userCommand.uuid = uuid
		userCommand.name = name
		userCommand.email = email
		then:"We expect the differences"
		result == validator.isValid(userCommand)
		where:"We have the next cases"
		uuid 																| name     													 | email 						  || result 
		"51d172788b034b738565f7dd4d4cb2fe"  | "josdem" 													 | "josdem@josdem.mx" || true
		"5"                                 | "josdem" 													 | "josdem@josdem.mx" || true
		"51d172788b034b738565f7dd4d4cb2fe1" | "josdem" 													 | "josdem@josdem.mx" || false
		"51d172788b034b738565f7dd4d4cb2fe"  | "josdem" 													 | "josdem.mx"        || false
		null                                | "josdem" 													 | "josdem@josdem.mx" || false
		""                                  | "josdem" 													 | "josdem@josdem.mx" || false
		"51d172788b034b738565f7dd4d4cb2fe"  | null     													 | "josdem@josdem.mx" || false
		"51d172788b034b738565f7dd4d4cb2fe"  | ""       													 | "josdem@josdem.mx" || false
		"51d172788b034b738565f7dd4d4cb2fe"  | "josdem" 													 | null               || false
		"51d172788b034b738565f7dd4d4cb2fe"  | "josdem" 													 | ""                 || true
		"<script>alert('josdem')</script>"  | "josdem" 													 | "josdem@josdem.mx" || false
		"51d172788b034b738565f7dd4d4cb2fe"  | "<script>alert('josdem')</script>" | "josdem@josdem.mx" || false
	}
	
}

