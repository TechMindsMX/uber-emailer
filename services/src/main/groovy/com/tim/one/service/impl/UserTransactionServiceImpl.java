package com.tim.one.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tim.one.bean.AdminAccountType;
import com.tim.one.bean.EntityType;
import com.tim.one.bean.MessageType;
import com.tim.one.bean.ProjectTxType;
import com.tim.one.bean.TramaAccountType;
import com.tim.one.bean.TransactionType;
import com.tim.one.collaborator.CalculatorCollaborator;
import com.tim.one.collaborator.UnitCollaborator;
import com.tim.one.exception.NotTramaAccountFoundException;
import com.tim.one.exception.ProjectNotExistException;
import com.tim.one.exception.UserNotFoundException;
import com.tim.one.model.ProjectFinancialData;
import com.tim.one.model.TramaAccount;
import com.tim.one.model.UnitTx;
import com.tim.one.model.User;
import com.tim.one.packer.MessagePacker;
import com.tim.one.repository.ProjectRepository;
import com.tim.one.repository.TramaAccountRepository;
import com.tim.one.repository.UserRepository;
import com.tim.one.service.ProjectTransactionService;
import com.tim.one.service.TransactionApplier;
import com.tim.one.service.TransactionLogService;
import com.tim.one.service.UnitService;
import com.tim.one.service.UserTransactionService;
import com.tim.one.state.ApplicationState;

/**
 * @author josdem
 * @understands A class who knows how to manage user's transactions
 * 
 */

@Service
public class UserTransactionServiceImpl implements UserTransactionService {
	
	@Autowired
	private TransactionLogService transactionLogService;
	@Autowired
	private UnitService unitService;
	@Autowired
	private UnitCollaborator unitCollaborator;
	@Autowired
	private ProjectTransactionService projectTransactionService;
	@Autowired
	private CalculatorCollaborator calculatorCollaborator;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private MessagePacker messagePacker;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private TramaAccountRepository tramaAccountRepository;
	@Autowired
	private TransactionApplier transactionApplier;
	
	@Autowired
  @Qualifier("properties")
  private Properties properties;
	
	public void measureReturnOfInvestment(Integer userId, ProjectFinancialData projectFinancialData, BigDecimal amount, TransactionType type) throws NotTramaAccountFoundException, UserNotFoundException, ProjectNotExistException {
		TramaAccount tramaAccount = tramaAccountRepository.findByType(AdminAccountType.TRAMA);
		if(tramaAccount == null){
			throw new NotTramaAccountFoundException();
		}
		
		User user = userRepository.findOne(userId);
    if (user == null){
      throw new UserNotFoundException(userId);
    }
    
    if(amount.compareTo(new BigDecimal(0.001)) > 0){
    	BigDecimal fee = calculatorCollaborator.computePercentage(amount, new BigDecimal(new Integer(properties.getProperty(ApplicationState.TRAMA_TRANSACTION_FEE))));
  		transactionApplier.addAmount(tramaAccount, fee);
  		transactionLogService.createTramaLog(userId, fee, EntityType.USER, TramaAccountType.INVESTMENT);
  		
  		transactionApplier.substractAmount(projectFinancialData, fee);
  		transactionLogService.createProjectLog(projectFinancialData.getProject().getId(), userId, fee, ProjectTxType.TRAMA_FEE);
  		
  		BigDecimal deposit = amount.subtract(fee);
  		Integer transactionId = transactionLogService.createUserLog(projectFinancialData.getProject().getId(), userId, null, deposit, null, type);
  		transactionApplier.addAmount(user, deposit);
  		
  		transactionApplier.substractAmount(projectFinancialData, deposit);
  		if(type.equals(TransactionType.RDF)){
  			transactionLogService.createProjectLog(projectFinancialData.getProject().getId(), userId, deposit, ProjectTxType.RDF);
  		} else {
  			transactionLogService.createProjectLog(projectFinancialData.getProject().getId(), userId, deposit, ProjectTxType.RDI);
  		}
  		
  		messagePacker.sendAbonoCuenta(deposit, projectFinancialData.getProject().getName(), user.getEmail(), transactionId, MessageType.CIERRE_PRODUCTO);
    }
	}
	
	public BigDecimal getRDI(Integer userId, ProjectFinancialData project){
		List<UnitTx> units = unitService.getUnitsByUserAndType(userId, TransactionType.INVESTMENT);
		BigDecimal amount = unitCollaborator.getUnitsAmount(project, units);
		return amount.multiply(projectTransactionService.getTRI(project));
	}
	
	public BigDecimal getRDF(Integer userId, ProjectFinancialData projectFinancialData){
		List<UnitTx> units = unitService.getUnitsByUserAndType(userId, TransactionType.FUNDING);
		BigDecimal amount = unitCollaborator.getUnitsAmount(projectFinancialData, units);
		return amount.multiply(projectTransactionService.getTRF(projectFinancialData));
	}
	
	public BigDecimal getRAC(Integer userId, ProjectFinancialData projectFinancialData){
		return projectTransactionService.getTRF(projectFinancialData).multiply(projectTransactionService.getMAC(projectFinancialData.getId()));
	}
	
}
