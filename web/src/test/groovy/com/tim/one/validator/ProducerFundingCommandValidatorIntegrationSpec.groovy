package com.tim.one.validator

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional

import spock.lang.Specification
import spock.lang.Unroll

import com.tim.one.command.ProducerFundingCommand
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
public class ProducerFundingCommandValidatorIntegrationSpec extends Specification {

	@Autowired
	CommandValidator validator

	@Unroll
	def """When we have a project with #projectId as id, and want to fund #amount 
			when we validate we expecting that result is: #result"""() {
		given: "An cash out command"
		def command = new ProducerFundingCommand()
		when: "We assing values to command"
		command.projectId = projectId
		command.amount = amount
		then:"We expect the differences"
		result == validator.isValid(command)
		where:"We have the next cases"
		projectId 	| amount  || result 
		1  			 	  | 0.01    || true
		0  			 	  | 0.01    || false
		null  			| 0.01    || false
		1  			 	  | 0.00    || false
		1  			 	  | null    || false
		1  			 	  | -10.00  || false
	}
	
}


