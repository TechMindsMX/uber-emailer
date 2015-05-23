package com.tim.one.validator

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional

import spock.lang.Specification
import spock.lang.Unroll

import com.tim.one.command.CreateAccountCommand
import com.tim.one.command.PaypalPaymentCommand
import com.tim.one.command.ProjectChangeStatusCommand
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
public class ProjectChangeStatusCommandValidatorIntegrationSpec extends Specification {

	@Autowired
	CommandValidator validator

	@Unroll
	def """When we have an project with id #projectId, and user with id #userId and
	status #status and reason as #reason and comment as #comment and
  when we validate we expecting that result is: #result"""() {
		given: "An change status command"
		def command = new ProjectChangeStatusCommand()
		when: "We assing values to command"
		command.projectId = projectId
		command.userId = userId
		command.status = status
		command.reason = reason
		command.comment = comment
		then:"We expect the following results"
		result == validator.isValid(command)
		where:"We have the next cases"
		projectId | userId	| status 	|	reason  | comment																	 || result 
		1					| 2  			| 0       | null 		| null    																 || true
		0					| 2  			| 0       | null 		| null    																 || false
		null		  | 2  			| 0       | null 		| null    																 || false
		1					| 0  			| 0       | null 		| null    																 || false
		1					| null  	| 0       | null 		| null    																 || false
		1					| 2  			| -1      | null 		| null    																 || false
		1					| 2  			| 12      | null 		| null    																 || false
		1					| 2  			| null    | null 		| null  																	 || false
		1					| 2  			| 0       | -1 			| null    																 || false
		1					| 2  			| 0       | 3 			| null    																 || false
		1					| 2  			| 0       | null 		| ""    																	 || false
		1					| 2  			| 0       | null 		| "<script>alert('hello josdem')</script>" || false
	}
	
}

