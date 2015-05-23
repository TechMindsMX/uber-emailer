package com.tim.one.validator

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional

import spock.lang.Specification
import spock.lang.Unroll

import com.tim.one.integra.command.IntegraCashinCommand
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
public class IntegraCashinCommandValidatorIntegrationSpec extends Specification {

	@Autowired
	CommandValidator validator

	@Unroll
	def """When we have a user with #uuid as id, and 
	is trying to transfer #amount when we validate we expecting that result is: #result"""() {
		given: "An cash out command"
		def command = new IntegraCashinCommand()
		when: "We assing values to command"
		command.uuid = uuid
		command.amount = amount
		then:"We expect the differences"
		result == validator.isValid(command)
		where:"We have the next cases"
		uuid 																|  amount  || result 
		"51d172788b034b738565f7dd4d4cb2fe"  |  0.01 	 || true
		"51d172788b034b738565f7dd4d4cb2f"   |  0.01 	 || false
		"51d172788b034b738565f7dd4d4cb2fea" |  0.01 	 || false
		"" 																	|  0.01 	 || false
		null 																|  0.01 	 || false
		"51d172788b034b738565f7dd4d4cb2fe"  |  0.00 	 || false
		"51d172788b034b738565f7dd4d4cb2fe"  |  -10.00  || false
		"51d172788b034b738565f7dd4d4cb2fe"  |  null  	 || false
		"<script>alert('josdem')</script>"  |  0.01 	 || false
	}
	
}

