package com.tim.one.validator

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional

import spock.lang.Specification
import spock.lang.Unroll

import com.tim.one.command.CreateAccountCommand
import com.tim.one.command.PaypalPaymentCommand
import com.tim.one.command.ProjectChangeStatusCommand
import com.tim.one.command.ProjectCreateFinancialDataCommand
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
public class ProjectCreateFinancialDataCommandValidatorIntegrationSpec extends Specification {

	@Autowired
	CommandValidator validator

	@Unroll
	def """When we have an project with id as #projectId, and this parameters #numberPublic, #eventCode, #section, #unitSale,
  #capacity, codeSection, #productionStartDate, #premiereStartDate, #premiereEndDate and
  when we validate we expecting that result is: #result"""() {
		given: "An change status command"
		def command = new ProjectCreateFinancialDataCommand()
		when: "We assing values to command"
		command.projectId = projectId
		command.numberPublic = numberPublic
		command.eventCode = eventCode
		command.section = section
		command.unitSale = unitSale
		command.capacity = capacity
		command.codeSection = codeSection
		command.productionStartDate = productionStartDate
		command.premiereStartDate = premiereStartDate
		command.premiereEndDate = premiereEndDate
		then:"We expect the following results"
		result == validator.isValid(command)
		where:"We have the next cases"
		projectId | numberPublic	| eventCode 	|	section   | unitSale	 | capacity   | codeSection   | productionStartDate   | premiereStartDate   | premiereEndDate   || result 
		1					| 0  						| "eventCode" | "section" | "unitSale" | "capacity"	| "codeSection" | "productionStartDate" | "premiereStartDate" | "premiereEndDate" || true
		0					| 0  						| "eventCode" | "section" | "unitSale" | "capacity"	| "codeSection" | "productionStartDate" | "premiereStartDate" | "premiereEndDate" || false
		null			| 0  						| "eventCode" | "section" | "unitSale" | "capacity"	| "codeSection" | "productionStartDate" | "premiereStartDate" | "premiereEndDate" || false
		1					| -1  					| "eventCode" | "section" | "unitSale" | "capacity"	| "codeSection" | "productionStartDate" | "premiereStartDate" | "premiereEndDate" || false
		1					| 2  						| "eventCode" | "section" | "unitSale" | "capacity"	| "codeSection" | "productionStartDate" | "premiereStartDate" | "premiereEndDate" || false
		1					| 0  						| null       	| "section" | "unitSale" | "capacity"	| "codeSection" | "productionStartDate" | "premiereStartDate" | "premiereEndDate" || true
		1					| 0  						| "eventCode" | null 			| "unitSale" | "capacity"	| "codeSection" | "productionStartDate" | "premiereStartDate" | "premiereEndDate" || true
		1					| 0  						| "eventCode" | "section" | null 			 | "capacity"	| "codeSection" | "productionStartDate" | "premiereStartDate" | "premiereEndDate" || true
		1					| 0  						| "eventCode" | "section" | "unitSale" | null				| "codeSection" | "productionStartDate" | "premiereStartDate" | "premiereEndDate" || true
		1					| 0  						| "eventCode" | "section" | "unitSale" | "capacity"	| null 					| "productionStartDate" | "premiereStartDate" | "premiereEndDate" || true
		1					| 0  						| "eventCode" | "section" | "unitSale" | "capacity"	| "codeSection" | null 									| "premiereStartDate" | "premiereEndDate" || true
		1					| 0  						| "eventCode" | "section" | "unitSale" | "capacity"	| "codeSection" | "productionStartDate" | null 								| "premiereEndDate" || true
		1					| 0  						| "eventCode" | "section" | "unitSale" | "capacity"	| "codeSection" | "productionStartDate" | "premiereStartDate" | null							|| true
	}
	
}

