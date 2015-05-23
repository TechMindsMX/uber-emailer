package com.tim.one.repository;

import static org.junit.Assert.assertFalse

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional

import spock.lang.Specification

import com.tim.one.model.BulkUnitTx
import com.tim.one.model.UnitTx

@ContextConfiguration(locations=["classpath:/persistence-appctx.xml"])
@Transactional
public class BulkUnitTxRepositoryIntegrationSpec extends Specification {

  @Autowired
  BulkUnitTxRepository repository

  def "should find a bulk unit tx by id"() {
    given: "A Bulk unit tx"
      BulkUnitTx bulkUnitTx = new BulkUnitTx(); 
    and: "Saving a bulk unit tx"
      repository.save(bulkUnitTx)
    when: "We find a bulk unit tx by id"
      BulkUnitTx result = repository.findById(bulkUnitTx.id)
    then: "We expect to find it"
      result.id == bulkUnitTx.id 
    cleanup:"We delete bulk unit tx"
      repository.delete(bulkUnitTx)
  }
  
  def "should find a bulk and units"() {
    given: "A Bulk unit tx"
      BulkUnitTx bulkUnitTx = new BulkUnitTx();
    and: "unit"
     UnitTx unitTx = new UnitTx()
   and: "Assigning units to bulk"
     def units = [unitTx] as Set
     bulkUnitTx.units = units
    and: "Saving a bulk unit tx"
      repository.save(bulkUnitTx)
    when: "We find a bulk unit tx by id"
      BulkUnitTx result = repository.findById(bulkUnitTx.id)
    then: "We expect to find it"
      result.units.size() == 1
      result.units[0] == unitTx
    cleanup:"We delete bulk unit tx"
      repository.delete(bulkUnitTx)
  }
  
}
