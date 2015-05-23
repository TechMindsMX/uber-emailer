package com.tim.one.repository;

import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import spock.lang.Specification;
import spock.lang.Unroll;

import com.tim.one.model.BankCode;
import com.tim.one.model.ProjectCategory;

@ContextConfiguration(locations=["classpath:/persistence-appctx.xml"])
public class BankCodeRepositoryIntegrationSpec extends Specification {

  @Autowired
  BankCodeRepository repository

  def "should find a bank code by code"() {
    given: "A BankCode"
      BankCode bankCode = new BankCode(); 
    and: "Assigning code to BankCode"
      Integer code = 1
      bankCode.bankCode = code
    and: "Saving bankCode"
      repository.save(bankCode)
    when: "We find a bankCode by code"
      BankCode result = repository.findByBankCode(code)
    then: "We expect to find it"
      result.bankCode == code 
    cleanup:"We delete bankCode"
      repository.delete(bankCode)
  }
  
}
