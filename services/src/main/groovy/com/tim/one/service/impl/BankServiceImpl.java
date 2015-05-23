package com.tim.one.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim.one.model.TramaAccount;
import com.tim.one.repository.TramaAccountRepository;
import com.tim.one.service.BankService;

/**
 * @author josdem
 * @understands A class who knows how receive a bank request and delegate it to
 *              DAO
 * 
 */

@Service
public class BankServiceImpl implements BankService {

  @Autowired
  private TramaAccountRepository repository;

  public Iterable<TramaAccount> getAdministrativeAccounts() {
    return repository.findAll();
  }

}
