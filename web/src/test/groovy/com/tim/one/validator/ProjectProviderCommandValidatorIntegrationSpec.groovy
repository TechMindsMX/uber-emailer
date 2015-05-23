package com.tim.one.validator

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional

import spock.lang.Specification
import spock.lang.Unroll

import com.tim.one.command.CreateAccountCommand
import com.tim.one.command.ProjectProviderCommand
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
public class ProjectProviderCommandValidatorIntegrationSpec extends Specification {

	@Autowired
	CommandValidator validator

	@Unroll
	def """When we have a project with id #projectId, and projectProvider as #projectProvider 
  when we validate we expecting that result is: #result"""() {
		given: "A project provider command"
		def command = new ProjectProviderCommand()
		when: "We assing values to command"
		command.projectId = projectId
		command.projectProvider = projectProvider
		then:"We expect the following results"
		result == validator.isValid(command)
		where:"We have the next cases"
		projectId	| projectProvider   				|| result 
		1  			| "projectProviders={id:1}" 	|| true
		0  			| "projectProviders={id:1}" 	|| false
		null  	| "projectProviders={id:1}" 	|| false
		1  			| "" 													|| false
		1  			| null 												|| false
	}
	
}

