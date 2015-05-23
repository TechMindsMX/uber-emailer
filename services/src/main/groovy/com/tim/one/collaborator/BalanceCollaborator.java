package com.tim.one.collaborator;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tim.one.bean.ProjectTxType;
import com.tim.one.bean.TransactionType;
import com.tim.one.exception.NonSufficientFundsException;
import com.tim.one.model.ProjectFinancialData;
import com.tim.one.model.User;
import com.tim.one.repository.ProjectFinancialDataRepository;
import com.tim.one.repository.UserRepository;
import com.tim.one.service.TransactionLogService;

@Component
public class BalanceCollaborator {

	@Autowired
	private TransactionLogService transactionLogService;
	@Autowired
	private ProjectFinancialDataRepository projectFinancialDataRepository;
	@Autowired
  private UserRepository userRepository;

	public Boolean afectProjectUserBalance(Integer projectId, Integer userId, BigDecimal total) throws NonSufficientFundsException {
		ProjectFinancialData project = projectFinancialDataRepository.findOne(projectId);
		if (project.getBalance().compareTo(total) == -1){
			throw new NonSufficientFundsException(project.getId());
		}
		
		transactionLogService.createProjectLog(projectId, userId, total, ProjectTxType.REDEEM_OUT);
		
		project.setBalance(project.getBalance().subtract(total));
		projectFinancialDataRepository.save(project);
		
		User user = userRepository.findOne(userId);
		user.setBalance(user.getBalance().add(total));
		userRepository.save(user);
		
		transactionLogService.createUserLog(projectId, userId, null, total, null, TransactionType.REDEEM_IN);
		return true;
	}

	public Boolean afectUserProjectBalance(Integer projectId, Integer userId, BigDecimal total) throws NonSufficientFundsException {
	  User user = userRepository.findOne(userId);
		if (user.getBalance().compareTo(total) == -1){
			throw new NonSufficientFundsException(user.getId());
		}
		user.setBalance(user.getBalance().subtract(total));
		userRepository.save(user);
		transactionLogService.createUserLog(projectId, userId, null, total, null, TransactionType.REDEEM_OUT);
		
		ProjectFinancialData project = projectFinancialDataRepository.findOne(projectId);
		project.setBalance(project.getBalance().add(total));
		projectFinancialDataRepository.save(project);
		
		transactionLogService.createProjectLog(projectId, userId, total, ProjectTxType.REDEEM_IN);
		return true;
	}
	
}
