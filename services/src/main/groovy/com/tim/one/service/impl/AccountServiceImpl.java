package com.tim.one.service.impl;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim.one.bean.AccountType;
import com.tim.one.bean.AdminAccountType;
import com.tim.one.bean.EntityType;
import com.tim.one.bean.ProjectTxType;
import com.tim.one.bean.ResultType;
import com.tim.one.bean.TramaAccountType;
import com.tim.one.bean.TransactionType;
import com.tim.one.collaborator.AccountCollaborator;
import com.tim.one.collaborator.AccountIdentifier;
import com.tim.one.exception.InvalidAmountException;
import com.tim.one.exception.NoAccountExistException;
import com.tim.one.exception.NonSufficientFundsException;
import com.tim.one.helper.TransactionHelper;
import com.tim.one.model.Project;
import com.tim.one.model.ProjectFinancialData;
import com.tim.one.model.TramaAccount;
import com.tim.one.model.User;
import com.tim.one.packer.MessagePacker;
import com.tim.one.repository.ProjectFinancialDataRepository;
import com.tim.one.repository.ProjectRepository;
import com.tim.one.repository.TramaAccountRepository;
import com.tim.one.repository.UserRepository;
import com.tim.one.service.AccountService;
import com.tim.one.service.TransactionApplier;
import com.tim.one.service.TransactionLogService;
import com.tim.one.stp.validator.ClabeValidator;

/**
 * @author josdem
 * @understands A class who knows how receive a account request and delegate it
 *              to DAO
 * 
 */

@Service
public class AccountServiceImpl implements AccountService {

  @Autowired
  private AccountIdentifier accountIdentifier;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private TransactionHelper transactionHelper;
  @Autowired
  private MessagePacker messagePacker;
  @Autowired
  private AccountCollaborator accountCollaborator;
  @Autowired
  private TransactionLogService transactionLogService;
  @Autowired
  private ClabeValidator clabeValidator;
  @Autowired
  private ProjectFinancialDataRepository projectFinancialDataRepository;
  @Autowired
  private ProjectRepository projectRepository;
  @Autowired
  private TramaAccountRepository tramaAccountRepository;
  @Autowired
  private TransactionApplier transactionApplier;

  public String getAccount(String account) {
    AccountType type = accountIdentifier.getTypeByAccount(account);
    if (type.equals(AccountType.USER)) {
      User user = userRepository.findUserByAccount(account);
      return user != null ? user.getName() : StringUtils.EMPTY;
    } else {
      ProjectFinancialData projectFinancialData = projectFinancialDataRepository.findProjectFinancialDataByAccount(account);
      if (projectFinancialData == null) {
        return StringUtils.EMPTY;
      }
      Project project = projectRepository.findOne(projectFinancialData.getId());
      return project.getName();
    }
  }

  public ResultType transferFundsFromTramaTo(String account, BigDecimal amount) throws NonSufficientFundsException,
      NoAccountExistException, InvalidAmountException {
    TramaAccount trama = tramaAccountRepository.findByType(AdminAccountType.TRAMA);
    if (trama.getBalance().compareTo(amount) < 0) {
      throw new NonSufficientFundsException(trama.getId());
    }

    AccountType type = accountIdentifier.getTypeByAccount(account);
    Integer entityId = null;
    EntityType entityType = EntityType.USER;
    if (type.equals(AccountType.USER)) {
      User user = userRepository.findUserByAccount(account);
      if (user == null) {
        throw new NoAccountExistException(account);
      }
      transactionApplier.addAmount(user, amount);
      entityId = user.getId();
      transactionLogService.createUserLog(null, user.getId(), null, amount, null, TransactionType.TRANSFER_FROM_TRAMA);
    } else {
      ProjectFinancialData project = projectFinancialDataRepository.findProjectFinancialDataByAccount(account);
      if (project == null) {
        throw new NoAccountExistException(account);
      }
      transactionApplier.addAmount(project, amount);
      entityId = project.getId();
      entityType = EntityType.PROJECT;
      transactionLogService.createProjectLog(project.getId(), null, amount, ProjectTxType.TRANSFER_FROM_TRAMA);
    }

    transactionLogService.createTramaLog(entityId, amount, entityType, TramaAccountType.TRANSFER_TO_PARTNER);
    transactionApplier.substractAmount(trama, amount);
    return ResultType.SUCCESS;
  }

}
