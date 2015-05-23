package com.tim.one.validator

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional

import spock.lang.Specification
import spock.lang.Unroll

import com.tim.one.command.StpCashInCommand
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
public class StpCashinCommandValidatorIntegrationSpec extends Specification {

	@Autowired
	CommandValidator validator

	@Unroll
	def """When we have an origin account as #clabeOrdenante, and destiny account as #clabeBeneficiario and 
	is trying to transfer #amount with this reference: #reference when we validate we expecting that result is: #result"""() {
		given: "An cash out command"
		def command = new StpCashInCommand()
		when: "We assing values to command"
		command.clabeOrdenante = clabeOrdenante
		command.clabeBeneficiario = clabeBeneficiario
		command.amount = amount
		command.reference = reference
		then:"We expect the differences"
		result == validator.isValid(command)
		where:"We have the next cases"
		clabeOrdenante 				| clabeBeneficiario     | reference 												 | amount  || result 
		"014180605638266336"  | "646180111901100023"  | "123" 										         | 0.01 		|| true
		""  									| "646180111901100023"  | "123" 													   | 0.01 		|| false
		null  								| "646180111901100023"  | "123" 														 | 0.01 		|| false
		"0141806056382663361" | "646180111901100023"  | "123" 														 | 0.01 		|| false
		"014180605638266336"  | "" 									  | "123" 													   | 0.01 		|| false
		"014180605638266336"  | null 								  | "123" 														 | 0.01 		|| false
		"014180605638266336"  | "6461801119011000231" | "123" 														 | 0.01 		|| false
		"014180605638266336"  | "646180111901100023"  | "123" 													   | 0.00 		|| false
		"014180605638266336"  | "646180111901100023"  | "123" 														 | null 		|| false
		"014180605638266336"  | "646180111901100023"  | "123" 														 | -10.00 	|| false
		"014180605638266336"  | "646180111901100023"  | "" 																 | 0.01 		|| false
		"014180605638266336"  | "646180111901100023"  | null 													     | 0.01 		|| false
		"<script></script>"   | "646180111901100023"  | "123" 														 | 0.01 		|| false
		"014180605638266336"  | "<script></script>"   | "123" 														 | 0.01 		|| false
		"014180605638266336"  | "646180111901100023"  | "<script>alert('josdem')</script>" | 0.01 		|| false
	}
	
}

