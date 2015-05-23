package com.tim.one.validator

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional

import spock.lang.Specification
import spock.lang.Unroll

import com.tim.one.command.CreateAccountCommand
import com.tim.one.command.PaypalPaymentCommand
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
public class PaypalPaymentCommandValidatorIntegrationSpec extends Specification {

	@Autowired
	CommandValidator validator

	@Unroll
	def """When we have an user with id #userId, and an amount to transfer #amount 
  when we validate we expecting that result is: #result"""() {
		given: "An transfer to paypal command"
		def command = new PaypalPaymentCommand()
		when: "We assing values to command"
		command.userId = userId
		command.amount = amount
		then:"We expect the following results"
		result == validator.isValid(command)
		where:"We have the next cases"
		userId	| amount 			|| result 
		1  			| 0.01        || true
		0  			| 0.01        || false
		null  	| 0.01        || false
		1  			| 0.00        || false
		1  			| -10.00      || false
		1  			| null        || false
	}
	
}

