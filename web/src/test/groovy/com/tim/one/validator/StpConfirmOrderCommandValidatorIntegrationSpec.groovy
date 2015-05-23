package com.tim.one.validator

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional

import spock.lang.Specification
import spock.lang.Unroll

import com.tim.one.command.StpConfirmOrderCommand
import com.tim.one.command.StpRegisterOrderCommand
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
public class StpConfirmOrderCommandValidatorIntegrationSpec extends Specification {

	@Autowired
	CommandValidator validator

	@Unroll
	def """When we have an transaction with #id as id, and clave rastreo as #claveRastreo and 
      timestamp #timestamp and estado as #estado and  
			when we validate we expecting that result is: #result"""() {
		given: "An cash out command"
		def command = new StpConfirmOrderCommand()
		when: "We assing values to command"
		command.id = id
		command.claveRastreo = claveRastreo
		command.timestamp = timestamp
		command.estado = estado
		then:"We expect the differences"
		result == validator.isValid(command)
		where:"We have the next cases"
		id 					| claveRastreo  | timestamp | estado || result 
		1  					| 123 					| 1L        | 0      || true
		0  					| 123 					| 1L        | 0      || false
		null  			| 123 					| 1L        | 0      || false
		1  					| 0 						| 1L        | 0      || false
		1  					| null 					| 1L        | 0      || false
		1  					| 123 					| 0L        | 0      || false
		1  					| 123 					| null      | 0      || false
		1  					| 123 					| 1L        | 1      || true
		1  					| 123 					| 1L        | 2      || false
	}
	
}


