package com.tim.one.validator

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional

import spock.lang.Specification
import spock.lang.Unroll

import com.tim.one.command.CreateAccountCommand
import com.tim.one.command.TransferAccountCommand

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
public class CreateAccountCommandValidatorIntegrationSpec extends Specification {

	@Autowired
	CommandValidator validator

	@Unroll
	def """When we have an user with id #userId, and bank code #bankCode, and clabe #clabe 
  when we validate we expecting that result is: #result"""() {
		given: "An create account command"
		def command = new CreateAccountCommand()
		when: "We assing values to command"
		command.userId = userId
		command.bankCode = bankCode
		command.clabe = clabe
		then:"We expect the following results"
		result == validator.isValid(command)
		where:"We have the next cases"
		userId	| bankCode   		 | clabe 																								 || result 
		1  			| 40014          | "014180605638266336" 																 || true
		null  	| 40014          | "014180605638266336" 																 || false
		0  			| 40014          | "014180605638266336" 																 || false
		1  			| 0          		 | "014180605638266336" 																 || false
		1  			| null       		 | "014180605638266336" 																 || false
		1  			| 40014    		   | "" 																									 || false
		1  			| 40014    		   | null 																								 || false
		1  			| 40014    		   | "646180111901200017646180111901200017646180111901213" || false
		1  			| 40014          | "<script>alert('hello josdem')</script>" 						 || false
		
	}
	
}

