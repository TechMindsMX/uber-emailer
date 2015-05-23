package com.tim.one.validator

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional

import spock.lang.Specification
import spock.lang.Unroll

import com.tim.one.command.MaxAmountCommand
import com.tim.one.command.ProviderPartnershipCommand
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
public class ProviderParnershipCommandValidatorIntegrationSpec extends Specification {

	@Autowired
	CommandValidator validator

	@Unroll
	def """When we have a provider with #providerId as id, and project with #projectId as id and want to register an transaction type 
			#type when we validate we expecting that result is: #result"""() {
		given: "An cash out command"
		def command = new ProviderPartnershipCommand()
		when: "We assing values to command"
		command.providerId = providerId
		command.projectId = projectId
		command.type = type
		then:"We expect the differences"
		result == validator.isValid(command)
		where:"We have the next cases"
		providerId 	| projectId 	| type  || result 
		1  					| 2 		      | 0  		|| true
		0  					| 2 		      | 0  		|| false
		null 				| 2 		      | 0  		|| false
		1  					| 0 		      | 0  		|| false
		1  					| null 		    | 0  		|| false
		1  					| 2 		      | null  || false
		1  					| 2 		      | 1  		|| true
		1  					| 2 		      | 2  		|| true
		1  					| 2 		      | 3  		|| false
	}
	
}


