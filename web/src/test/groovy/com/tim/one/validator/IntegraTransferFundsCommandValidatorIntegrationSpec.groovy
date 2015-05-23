package com.tim.one.validator

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional

import spock.lang.Specification
import spock.lang.Unroll

import com.tim.one.integra.command.IntegraCashoutCommand
import com.tim.one.integra.command.IntegraTransferFundsCommand
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
public class IntegraTransferFundsCommandValidatorIntegrationSpec extends Specification {

	@Autowired
	CommandValidator validator

	@Unroll
	def """When we have a user with #uuidOrigin as origin account, and user with #uuidDestination as destiny account 
	and first is trying to transfer #amount to the destination account the result is: #result"""() {
		given: "An transfer funds command"
		def command = new IntegraTransferFundsCommand()
		when: "We assing values to command"
		command.uuidOrigin = uuidOrigin
		command.uuidDestination = uuidDestination
		command.amount = amount
		then:"We expect the differences"
		result == validator.isValid(command)
		where:"We have the next cases"
     uuidOrigin 												| uuidDestination      							 | amount  || result 
		"51d172788b034b738565f7dd4d4cb2fe"  | "9f76bd8b6ca846d397cec03cdc66f6dd" | 0.01 	 || true
		"5"  																| "9f76bd8b6ca846d397cec03cdc66f6dd" | 0.01 	 || false
		""  																| "9f76bd8b6ca846d397cec03cdc66f6dd" | 0.01 	 || false
		null  															| "9f76bd8b6ca846d397cec03cdc66f6dd" | 0.01 	 || false
		"51d172788b034b738565f7dd4d4cb2fe"  | "9" 															 | 0.01 	 || false
		"51d172788b034b738565f7dd4d4cb2fe"  | ""	 															 | 0.01 	 || false
		"51d172788b034b738565f7dd4d4cb2fe"  | null 															 | 0.01 	 || false
		"51d172788b034b738565f7dd4d4cb2fe"  | "9f76bd8b6ca846d397cec03cdc66f6dd" | 0.00 	 || false
		"51d172788b034b738565f7dd4d4cb2fe"  | "9f76bd8b6ca846d397cec03cdc66f6dd" | -10.00  || false
		"51d172788b034b738565f7dd4d4cb2fe"  | "9f76bd8b6ca846d397cec03cdc66f6dd" | null    || false
		"<script>alert('josdem')</script>"  | "9f76bd8b6ca846d397cec03cdc66f6dd" | 0.01 	 || false
		"51d172788b034b738565f7dd4d4cb2fe"  | "<script>alert('josdem')</script>" | 0.01 	 || false
	}
	
}

