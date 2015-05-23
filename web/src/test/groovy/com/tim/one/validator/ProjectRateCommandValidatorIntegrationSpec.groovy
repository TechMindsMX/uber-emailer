package com.tim.one.validator

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional

import spock.lang.Specification
import spock.lang.Unroll

import com.tim.one.command.CreateAccountCommand
import com.tim.one.command.PaypalPaymentCommand
import com.tim.one.command.ProjectChangeStatusCommand
import com.tim.one.command.ProjectRateCommand
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
public class ProjectRateCommandValidatorIntegrationSpec extends Specification {

	@Autowired
	CommandValidator validator

	@Unroll
	def """When we have an project with id #projectId, and user with id #userId and score #score and 
  when we validate we expecting that result is: #result"""() {
		given: "An change status command"
		def command = new ProjectRateCommand()
		when: "We assing values to command"
		command.projectId = projectId
		command.userId = userId
		command.score = score
		then:"We expect the following results"
		result == validator.isValid(command)
		where:"We have the next cases"
		projectId | userId	| score 	|| result 
		1					| 2  			| 0       || true
		0					| 2  			| 0       || false
		null			| 2  			| 0       || false
		1					| 0  			| 0       || false
		1					| null  	| 0       || false
		1					| 2  			| -1      || false
		1					| 2  			| 6       || false
		1					| 2  			| null    || false
	}
	
}

