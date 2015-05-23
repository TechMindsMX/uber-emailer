package com.tim.one.validator

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional

import spock.lang.Specification
import spock.lang.Unroll

import com.tim.one.command.CapitalReturnCommand
import com.tim.one.command.StpRegisterOrderCommand
import com.tim.one.command.TransferFundsCommand
import com.tim.one.integra.command.IntegraCashoutCommand
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
public class CapitalReturnCommandValidatorIntegrationSpec extends Specification {

	@Autowired
	CommandValidator validator

	@Unroll
	def """When we have a project with #projectId as id, and provider with #providerId as id and want to return
			capital with amount #amount and 
			when we validate we expecting that result is: #result"""() {
		given: "An cash out command"
		def command = new CapitalReturnCommand()
		when: "We assing values to command"
		command.projectId = projectId
		command.providerId = providerId
		command.amount = amount
		then:"We expect the differences"
		result == validator.isValid(command)
		where:"We have the next cases"
		projectId 	| providerId 	| amount  || result 
		1  					| 2 		      | 0.01    || true
		0  					| 2 		      | 0.01    || false
		null 				| 2 		      | 0.01    || false
		1  					| 0 		      | 0.01    || false
		0  					| null 	      | 0.01    || false
		1  					| 2 		      | 0.00    || false
		1  					| 2 		      | null    || false
		1  					| 2 		      | -10.00  || false
	}
	
}


