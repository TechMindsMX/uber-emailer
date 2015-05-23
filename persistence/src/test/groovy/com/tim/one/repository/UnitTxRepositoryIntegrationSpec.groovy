package com.tim.one.repository;

import static org.junit.Assert.assertFalse

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.InvalidDataAccessResourceUsageException
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional

import spock.lang.Specification
import spock.lang.Unroll

import com.tim.one.bean.TransactionType
import com.tim.one.model.UnitTx

@ContextConfiguration(locations=["classpath:/persistence-appctx.xml"])
@Transactional
public class UnitTxRepositoryIntegrationSpec extends Specification {

  @Autowired
  UnitTxRepository repository

  def "should find project unit tx by userId"() {
    given: "A Unit tx"
      UnitTx unitTx = new UnitTx() 
    and: "Assigning userId to UnitTx"
      Integer userId = 1
      unitTx.setUserId(userId)
    and: "Saving a unit tx"
      repository.save(unitTx)
    when: "We find a unit tx by userId"
      def units = repository.findUnitsByUserId(userId)
      units
    then: "We expect to find it"
      units.size() == 1
    cleanup:"We delete unit tx"
      repository.delete(unitTx)
  }

  def "should find unit tx by project unit sale id"() {
    given: "A Unit tx"
      UnitTx unitTx = new UnitTx()
    and: "Assigning projectUnitSaleId to UnitTx"
      Integer projectUnitSaleId = 1
      unitTx.setProjectUnitSaleId(projectUnitSaleId)
    and: "Saving a unit tx"
      repository.save(unitTx)
    when: "We find a unit tx by userId"
      def result = repository.findUnitsByProjectUnitSaleId(projectUnitSaleId)
    then: "We expect to find it"
      result.size() == 1
    cleanup:"We delete unit tx"
      repository.delete(unitTx)
  }
  
  def "should find unit tx by type and user"() {
    given: "A Unit tx"
      UnitTx unitTx = new UnitTx()
    and: "Assigning type to UnitTx"
      TransactionType type = TransactionType.BUYING
      unitTx.type = type
    and: "Assigning user to UnitTx"
      Integer userId = 1
      unitTx.userId = userId
    and: "Saving a unit tx"
      repository.save(unitTx)
    when: "We find a unit tx by userId"
      def result = repository.findUnitsByTypeAndUserId(type, userId)
    then: "We expect to find it"
      result.size() == 1
    cleanup:"We delete unit tx"
      repository.delete(unitTx)
  }
  
  def "should find unit tx by projectUnitSales"() {
    given: "Two Unit tx"
      UnitTx unitTx = new UnitTx()
      UnitTx unitTx1 = new UnitTx()
    and: "Assigning unitSaleIds"
      unitTx.projectUnitSaleId = 0
      unitTx1.projectUnitSaleId = 1
      List<Integer> ids = new ArrayList<Integer>()
      ids.add(0)
      ids.add(1)
    and: "Saving units"
      repository.save(unitTx)
      repository.save(unitTx1)
    when: "We find units by ids"
      List<UnitTx> units = repository.findUnitsByprojectUnitSaleIdIn(ids);
    then: "We expect to find it"
      units.size() == 2
    cleanup:"We delete units"
      repository.delete(unitTx)
      repository.delete(unitTx1)
  }
  
  @Unroll
  def "should find unit tx by type and projectUnitSales"() {
    given: "n Unit tx"
    def unitTxs = projectUnitSaleIds.collect { projectUnitSaleId ->
      new UnitTx(projectUnitSaleId:projectUnitSaleId,type:TransactionType.FUNDING)
    }
    and: "Saving units"
      unitTxs.each { ut -> repository.save(ut) }
    when: "We find units by ids"
      List<UnitTx> units = repository.findUnitsByTypeAndProjectUnitSales(searchingType, projectUnitSaleIds)
    then: "We expect to find it"
      units.size() == projectUnitSaleIds.size()
    cleanup:
      unitTxs.each { ut -> repository.delete(ut) }
    where:
    projectUnitSaleIds | searchingType                         
    [0,1]              | TransactionType.FUNDING   
    [0]                | TransactionType.FUNDING   
  }

  
  def "should find error unit tx by type and projectUnitSales when ids are empty"() {
    given: "n Unit tx"
      UnitTx unitTx = new UnitTx()
    and: "Saving units"
      repository.save(unitTx)
    when: "We find units by ids"
      List<UnitTx> units = repository.findUnitsByTypeAndProjectUnitSales(TransactionType.FUNDING, [])
    then: "We expect to find it"
      thrown InvalidDataAccessResourceUsageException
    cleanup:
      repository.delete(unitTx)
  }
  
  def "should find project unit tx by userId and dates"() {
    given: "A Unit tx"
      UnitTx unitTx = new UnitTx()
    and: "Assigning userId to UnitTx"
      Integer userId = 1
      unitTx.userId = userId
    and: "Assigning timestamp to UnitTx"
      Long timestamp = 1L
      unitTx.timestamp = timestamp
    and: "Saving a unit tx"
      repository.save(unitTx)
    when: "We find a unit tx by userId"
      def units = repository.findByUserAndDate(userId, 0L, 2L)
    then: "We expect to find it"
      units.size() == 1
    cleanup:"We delete unit tx"
      repository.delete(unitTx)
  }
  
}

