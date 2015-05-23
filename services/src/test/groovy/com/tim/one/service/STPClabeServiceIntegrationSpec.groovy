package com.tim.one.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional

import spock.lang.Specification

import com.tim.one.stp.service.STPClabeService
import com.tim.one.stp.state.ApplicationState;

@ContextConfiguration(locations=[
	"classpath:/stp-appctx.xml",
	"classpath:/services-appctx.xml",
	"classpath:/persistence-appctx.xml",
	"classpath:/jms-appctx.xml",
	"classpath:/mail-context.xml",
	"classpath:/transaction-appctx.xml",
	"classpath:/formatter-appctx.xml",
	"classpath:/stp-appctx.xml",
	"classpath:/aop-appctx.xml",
	"classpath:/properties-appctx.xml"
	])

@Transactional
public class STPClabeServiceIntegrationSpec extends Specification {

	@Autowired
	STPClabeService userService

	def "Should generate and STP account"() {
		given: "An user account id"
		def userAccountId = 1
		def stpPrefix = "6461801119010"
		when: "We create an account"
		def account = userService.generateSTPAccount(userAccountId, ApplicationState.STP_FREAKFUND_PREFIX)
		then:"We expect a valid STP account"
		account == "646180111901100010"
	}

}

