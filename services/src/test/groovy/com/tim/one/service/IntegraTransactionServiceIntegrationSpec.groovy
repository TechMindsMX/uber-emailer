package com.tim.one.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional

import spock.lang.Specification

import com.tim.one.bean.IntegraTransactionType;
import com.tim.one.exception.BusinessException
import com.tim.one.model.IntegraUser
import com.tim.one.model.IntegraUserTx
import com.tim.one.repository.IntegraUserRepository
import com.tim.one.repository.IntegraUserTxRepository

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
public class IntegraTransactionServiceIntegrationSpec extends Specification {

	@Autowired
	IntegraTransactionService service
	@Autowired
	IntegraUserRepository repository
	@Autowired
	IntegraUserTxRepository userTxrepository

	def "Should not transfer to integra user since origin not exist"() {
		given: "An integra origin uuid"
		def originUuid = "originUuid"
		and: "Destination uuid"
		def destinationUuid = "destinationUuid"
		and: "Amount"
		def amount = new BigDecimal(100.00)
		when: "We want to transfer amount"
		service.transfer(originUuid, destinationUuid, amount)
		then:"We expect exception"
		thrown BusinessException
	}
	
	def "Should not transfer to integra user since destination not exist"() {
		given: "An integra origin uuid"
		def originUuid = "originUuid"
		def sender = new IntegraUser()
		sender.integraUuid = "originUuid"
		repository.save(sender)
		and: "Destination uuid"
		def destinationUuid = "destinationUuid"
		and: "Amount"
		def amount = new BigDecimal(100.00)
		when: "We want to transfer amount"
		service.transfer(originUuid, destinationUuid, amount)
		then:"We expect exception"
		thrown BusinessException
		cleanup:"We delete user"
		repository.delete(sender)
	}
	
	def "Should not transfer to integra user since sender has not funds"() {
		given: "An integra origin uuid"
		def originUuid = "originUuid"
		def sender = new IntegraUser()
		sender.integraUuid = "originUuid"
		repository.save(sender)
		and: "Destination uuid"
		def destinationUuid = "destinationUuid"
		def receiver = new IntegraUser()
		receiver.integraUuid = "destinationUuid"
		repository.save(receiver)
		and: "Amount"
		def amount = new BigDecimal(100.00)
		when: "We want to transfer amount"
		service.transfer(originUuid, destinationUuid, amount)
		then:"We expect exception"
		thrown BusinessException
		cleanup:"We delete user"
		repository.delete(sender)
		repository.delete(receiver)
	}
	
	def "Should transfer to integra user"() {
		given: "An integra origin uuid"
		def originUuid = "originUuid"
		def sender = new IntegraUser()
		sender.timoneUuid = "originUuid"
		sender.balance = new BigDecimal(500.00)
		repository.save(sender)
		and: "Destination uuid"
		def destinationUuid = "destinationUuid"
		def receiver = new IntegraUser()
		receiver.timoneUuid = "destinationUuid"
		repository.save(receiver)
		and: "Amount"
		def amount = new BigDecimal(100.00)
		when: "We want to transfer amount"
		def transactionId = service.transfer(originUuid, destinationUuid, amount)
		then:"We expect transfer transaction"
		sender.balance == new BigDecimal(400.00)
		receiver.balance == new BigDecimal(100.00)
		IntegraUserTx userTx = userTxrepository.findByUuid(transactionId)
		userTx.origin == "originUuid"
		userTx.destination == "destinationUuid"
		userTx.amount == new BigDecimal(100.00)
		userTx.timestamp > 0
		userTx.type == IntegraTransactionType.TRANSFER
		cleanup:"We delete user"
		repository.delete(sender)
		repository.delete(receiver)
		userTxrepository.delete(userTx)
	}
	
	def "Should not cashin integra user since user not exist"() {
		given: "An integra origin uuid"
		def uuid = "uuid"
		and: "Amount"
		def amount = new BigDecimal(100)
		when: "We want to cashin amount"
		service.cashIn(uuid, amount)
		then:"We expect exception"
		thrown BusinessException
	}

	def "Should do cashin from integra user"() {
		given: "An integra origin uuid"
		def uuid = "uuid"
		and: "Amount"
		def amount = new BigDecimal(100)
		and: "An user"
		def user = new IntegraUser()
		user.setTimoneUuid(uuid)
		and: "When we save user"
		repository.save(user)
		when: "We want to cashin amount"
		service.cashIn(uuid, amount)
		then:"We expect exception"
		user.getBalance().equals(amount)
		cleanup:"We delete user"
		repository.delete(user)
	}
}

