package com.tim.one.validator

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional

import spock.lang.Specification
import spock.lang.Unroll

import com.tim.one.command.CreateAccountCommand
import com.tim.one.command.PaypalPaymentCommand
import com.tim.one.command.ProjectChangeStatusCommand
import com.tim.one.command.ProjectDeleteUnitSaleCommand
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
public class ProjectDeleteUnitSaleCommandValidatorIntegrationSpec extends Specification {

	@Autowired
	CommandValidator validator

	@Unroll
	def """When we have an project unit sale with id #projectUnitSaleId
  when we validate we expecting that result is: #result"""() {
		given: "An change status command"
		def command = new ProjectDeleteUnitSaleCommand()
		when: "We assing values to command"
		command.projectUnitSaleId = projectUnitSaleId
		then:"We expect the following results"
		result == validator.isValid(command)
		where:"We have the next cases"
		projectUnitSaleId || result 
		1									|| true
		0									|| false
		null							|| false
	}
	
}

