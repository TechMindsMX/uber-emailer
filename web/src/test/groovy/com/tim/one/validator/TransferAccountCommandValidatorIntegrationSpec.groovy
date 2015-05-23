package com.tim.one.validator

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional

import spock.lang.Specification
import spock.lang.Unroll

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
public class TransferAccountCommandValidatorIntegrationSpec extends Specification {

	@Autowired
	CommandValidator validator

	@Unroll
	def """When we have an account #account, and amount #amount 
  when we validate we expecting that result is #result"""() {
		given: "An integra user command"
		def command = new TransferAccountCommand()
		when: "We assing values to command"
		command.account = account
		command.amount = amount
		then:"We expect the following results"
		result == validator.isValid(command)
		where:"We have the next cases"
		account				 														| amount   || result 
		"U1500000001"  														| 0.01     || true
		""  					 														| 0.01     || false
		null  				 														| 0.01     || false
		"U1500000001"  														| 0.00     || false
		"U1500000001"  														| -10.00   || false
		"U1500000001"  														| null   	|| false
		"<script>alert('hello josdem')</script>"  | null   	|| false
	}
	
}

