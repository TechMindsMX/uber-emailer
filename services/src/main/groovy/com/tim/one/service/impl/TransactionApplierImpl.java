package com.tim.one.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim.one.exception.InvalidAmountException;
import com.tim.one.model.IntegraUser;
import com.tim.one.model.ProjectFinancialData;
import com.tim.one.model.TramaAccount;
import com.tim.one.model.User;
import com.tim.one.repository.IntegraUserRepository;
import com.tim.one.repository.ProjectFinancialDataRepository;
import com.tim.one.repository.TramaAccountRepository;
import com.tim.one.repository.UserRepository;
import com.tim.one.service.TransactionApplier;
import com.tim.one.service.TransactionLogService;
import com.tim.one.validator.AmountValidator;

@Service
public class TransactionApplierImpl implements TransactionApplier {

  @Autowired
  private AmountValidator amountValidator;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private IntegraUserRepository integraUserRepository;
	@Autowired
	private TransactionLogService transactionLogService;
	@Autowired
  private ProjectFinancialDataRepository projectFinancialDataRepository;
	@Autowired
  private TramaAccountRepository tramaAccountRepository;

	@Override
	public void addAmount(User user, BigDecimal amount) throws InvalidAmountException {
	  amountValidate(amount);
		user.setBalance(user.getBalance().add(amount));
		userRepository.save(user);
	}
	
	@Override
  public void addAmount(TramaAccount trama, BigDecimal amount) {
    amountValidate(amount);
    trama.setBalance(trama.getBalance().add(amount));
    tramaAccountRepository.save(trama);
  }
	
	@Override
  public void addAmount(ProjectFinancialData project, BigDecimal amount) throws InvalidAmountException{
    amountValidate(amount);
    project.setBalance(project.getBalance().add(amount));
    projectFinancialDataRepository.save(project);
  }
	
	@Override
	public void addAmount(IntegraUser user, BigDecimal amount) throws InvalidAmountException {
	  amountValidate(amount);
		user.setBalance(user.getBalance().add(amount));
		integraUserRepository.save(user);
	}

  @Override
  public void substractAmount(ProjectFinancialData project, BigDecimal amount) throws InvalidAmountException{
    amountValidate(amount);
    project.setBalance(project.getBalance().subtract(amount));
    projectFinancialDataRepository.save(project);
  }

  @Override
  public void substractAmount(TramaAccount trama, BigDecimal amount) {
    amountValidate(amount);
    trama.setBalance(trama.getBalance().subtract(amount));
    tramaAccountRepository.save(trama);
  }
  
  @Override
  public void substractAmount(User user, BigDecimal amount) throws InvalidAmountException {
	  amountValidate(amount);
		user.setBalance(user.getBalance().subtract(amount));
		userRepository.save(user);
	}

	@Override
	public void substractAmount(IntegraUser user, BigDecimal amount) {
		amountValidate(amount);
		user.setBalance(user.getBalance().subtract(amount));
		integraUserRepository.save(user);
	}

	@Override
	public Boolean hasFunds(IntegraUser user, BigDecimal amount) {
		if (user.getBalance().compareTo(amount) < 0){
			return false;
		}
		return true;
	}
	
	@Override
	public Boolean hasFunds(User user, BigDecimal amount) {
		if (user.getBalance().compareTo(amount) < 0){
			return false;
		}
		return true;
	}
	
	private void amountValidate(BigDecimal amount) {
    if(!amountValidator.isValid(amount)){
      throw new InvalidAmountException(amount);
    }
  }

}
