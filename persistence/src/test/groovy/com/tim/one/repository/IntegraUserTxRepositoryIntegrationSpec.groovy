package com.tim.one.repository

import static org.junit.Assert.assertFalse

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional

import spock.lang.Specification

import com.tim.one.bean.IntegraTransactionType;
import com.tim.one.model.IntegraUser
import com.tim.one.model.IntegraUserTx

@ContextConfiguration(locations=["classpath:/persistence-appctx.xml"])
@Transactional
public class IntegraUserTxRepositoryIntegrationSpec extends Specification {

  @Autowired
  IntegraUserTxRepository repository
	
	def "should find user's tx by uuid"() {
		given: "A User tx"
			def userTx = new IntegraUserTx()
		and: "Assigning an uuid user tx"
			def uuid = "uuid"
			userTx.uuid = uuid
		and: "Saving an user tx"
			repository.save(userTx)
		when: "We find an user by uuid"
			IntegraUserTx result = repository.findByUuid(uuid)
		then: "We expect to find it"
			result.uuid == uuid
		cleanup:"We delete user"
			repository.delete(userTx)
	}

  def "should find user's txs by origin uuid"() {
    given: "A User tx"
      def userTx = new IntegraUserTx()
    and: "Assigning data to user tx"
			def originUuid = "originUuid"
      userTx.origin = originUuid
			userTx.destination = "destinationUuid"
			userTx.type = IntegraTransactionType.TRANSFER
    and: "Saving an user tx"
      repository.save(userTx)
    when: "We find an user by uuid"
      List<IntegraUserTx> result = repository.findTransactionsByUuid(originUuid)
    then: "We expect to find it"
			result.size() == 1
      result[0].origin == originUuid 
			result[0].type == IntegraTransactionType.TRANSFER
    cleanup:"We delete user"
      repository.delete(userTx)
  }
	
	def "should find user's txs by destination uuid"() {
		given: "A User tx"
			def userTx = new IntegraUserTx()
		and: "Assigning data to user tx"
			def destinationUuid = "destinationUuid"
			userTx.origin = "originUuid"
			userTx.destination = destinationUuid
			userTx.type = IntegraTransactionType.CASH_OUT
		and: "Saving an user tx"
			repository.save(userTx)
		when: "We find an user by uuid"
			List<IntegraUserTx> result = repository.findTransactionsByUuid(destinationUuid)
		then: "We expect to find it"
			result.size() == 1
			result[0].destination == destinationUuid
			result[0].type == IntegraTransactionType.CASH_OUT
		cleanup:"We delete user"
			repository.delete(userTx)
	}
	
	def "should find user's txs by uuid and date"() {
		given: "Two User tx"
			def userTx1 = new IntegraUserTx()
			def userTx2 = new IntegraUserTx()
		and: "Assigning data to user tx"
			def originUuid = "originUuid"
			userTx1.origin = originUuid
			userTx1.destination = "destinationUuid"
			userTx1.type = IntegraTransactionType.TRANSFER
			userTx1.timestamp = 1
			
			userTx2.origin = originUuid
			userTx2.destination = "destinationUuid"
			userTx2.type = IntegraTransactionType.TRANSFER
			userTx2.timestamp = 3
		and: "Saving an user txs"
			repository.save(userTx1)
			repository.save(userTx2)
		when: "We find an user by uuid"
			List<IntegraUserTx> result = repository.findTransactionsByUuidAndDate(originUuid, 0, 1)
		then: "We expect to find it"
			result.size() == 1
			result[0].origin == originUuid
			result[0].type == IntegraTransactionType.TRANSFER
			result[0].timestamp == 1
		cleanup:"We delete user"
			repository.delete(userTx1)
			repository.delete(userTx2)
	}

}
