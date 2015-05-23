package com.tim.one.validator

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional

import spock.lang.Specification
import spock.lang.Unroll

import com.tim.one.command.CreateAccountCommand
import com.tim.one.command.ProjectProviderCommand
import com.tim.one.command.ProjectProviderPaymentCommand
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
public class ProjectProviderPaymentCommandValidatorIntegrationSpec extends Specification {

	@Autowired
	CommandValidator validator

	@Unroll
	def """When we have a project with id #projectId, and provider with id #providerId, and type as #type 
  when we validate we expecting that result is: #result"""() {
		given: "A project provider command"
		def command = new ProjectProviderPaymentCommand()
		when: "We assing values to command"
		command.projectId = projectId
		command.providerId = providerId
		command.type = type
		then:"We expect the following results"
		result == validator.isValid(command)
		where:"We have the next cases"
		projectId	| providerId   		| type		|| result 
		1  			  | 2 							| 0				|| true
		0  			  | 2 							| 0				|| false
		null  		| 2 							| 0				|| false
		1  			  | 0 							| 0				|| false
		1  			  | null 						| 0			  || false
		1  			  | 2 							| 3				|| false
		1  			  | 2 							| -1			|| false
		1  			  | 2 							| null		|| false
	}
	
}

