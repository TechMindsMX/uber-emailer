package com.tim.one.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tim.one.bean.AdminAccountType;
import com.tim.one.bean.IntegraTransactionType;
import com.tim.one.bean.ResultType;
import com.tim.one.bean.STPBean;
import com.tim.one.bean.TransactionType;
import com.tim.one.collaborator.AccountCollaborator;
import com.tim.one.collaborator.STPConsumerCollaborator;
import com.tim.one.exception.AccountNotFoundException;
import com.tim.one.exception.InvalidAmountException;
import com.tim.one.exception.NonSufficientFundsException;
import com.tim.one.exception.UserNotFoundException;
import com.tim.one.model.BankCode;
import com.tim.one.model.IntegraUser;
import com.tim.one.model.StpStatus;
import com.tim.one.model.TramaAccount;
import com.tim.one.model.User;
import com.tim.one.model.UserAccount;
import com.tim.one.packer.CommonMessagePacker;
import com.tim.one.payment.bean.PaymentBean;
import com.tim.one.repository.BankCodeRepository;
import com.tim.one.repository.IntegraUserRepository;
import com.tim.one.repository.StpStatusRepository;
import com.tim.one.repository.TramaAccountRepository;
import com.tim.one.repository.UserAccountRepository;
import com.tim.one.repository.UserRepository;
import com.tim.one.service.CashinStrategy;
import com.tim.one.service.STPConsumerService;
import com.tim.one.service.TransactionApplier;
import com.tim.one.service.TransactionLogService;
import com.tim.one.stp.exception.STPResponseNotExists;
import com.tim.one.stp.exception.STPResponseNotValid;
import com.tim.one.stp.service.STPService;
import com.tim.one.stp.state.ApplicationState;
import com.tim.one.util.DateUtil;
import com.tim.one.validator.AmountValidator;
import com.tim.one.validator.STPValidator;

@Service
public class STPConsumerServiceImpl implements STPConsumerService {
  
  @Autowired
  private UserAccountRepository userAccountRepository;
  @Autowired
  private TransactionApplier transactionApplier;
  @Autowired
  private TransactionLogService transactionLogService;
  @Autowired
  private STPConsumerCollaborator stpCollaborator;
  @Autowired
  private CommonMessagePacker messagePacker;
  @Autowired
  private AccountCollaborator accountCollaborator;
  @Autowired
  private StpStatusRepository stpStatusRepository;
  @Autowired
  private STPService stpService;
  @Autowired
  private STPValidator stpValidator;
  @Autowired
  private DateUtil dateUtil;
  @Autowired
  private BankCodeRepository bankCodeRepository;
  @Autowired
  private TramaAccountRepository tramaAccountRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private IntegraUserRepository integraUserRepository;
  @Autowired
  private AmountValidator amountValidator;
  
  @Autowired
  @Qualifier("freakfundCashinStrategy")
	private CashinStrategy freakfundCashinStrategy;
  
  @Autowired
  @Qualifier("integraCashinStrategy")
	private CashinStrategy integraCashinStrategy;
  
  public Integer cashOut(Integer userId, BigDecimal amount){
    User user = userRepository.findOne(userId);
    if (user == null){
      throw new UserNotFoundException(userId);
    }
    
    UserAccount account = userAccountRepository.findByUser(user);
    if (account == null){
      throw new AccountNotFoundException(userId);
    }
    
    if (!transactionApplier.hasFunds(user, amount)){
      throw new NonSufficientFundsException(userId);
    }

    Integer speiId = stpService.registraOrden(user.getName(), user.getEmail(), account.getBankCode(), account.getClabe(), amount);
    
    transactionApplier.substractAmount(user, amount);
    transactionLogService.createUserLog(null, user.getId(), null, amount, null, TransactionType.CASH_OUT);
    StpStatus stpStatus = stpCollaborator.createOrdenStatus(speiId, userId, null, amount);
    messagePacker.sendCashout(amount, accountCollaborator.getBankName(account.getBankCode()), user.getEmail(), speiId);
    stpStatusRepository.save(stpStatus);
    return speiId;
  }
  
  public String cashOut(String uuid, String clabe, Integer bankCode, BigDecimal amount){
    IntegraUser user = integraUserRepository.findByTimoneUuid(uuid);
    if (user == null){
      throw new UserNotFoundException(1);
    }
    
    if (!transactionApplier.hasFunds(user, amount)){
      throw new NonSufficientFundsException(1);
    }

    Integer speiId = stpService.registraOrden(user.getName(), user.getEmail(), bankCode, clabe, amount);
    transactionApplier.substractAmount(user, amount);
    String transactionUuid = transactionLogService.createIntegraUserLog(uuid, uuid, amount, null, IntegraTransactionType.CASH_OUT);
    StpStatus stpStatus = stpCollaborator.createOrdenStatus(speiId, null, uuid, amount);
    stpStatusRepository.save(stpStatus);
    return transactionUuid;
  }
  
  public ResultType confirmaOrden(Integer id, Integer claveRastreo, Integer estado, Long timestamp) {
    StpStatus stpStatus = stpStatusRepository.findById(id);
    
    if(stpStatus == null){
      throw new STPResponseNotExists(id);
    }
    
    if (!stpValidator.isValid(estado)){
      throw new STPResponseNotValid(id);
    }
    
    stpStatus.setClaveRastreo(claveRastreo);
    stpStatus.setEstado(estado);
    stpStatus.setServerTimestamp(timestamp);
    stpStatus.setConfirmationTimestamp(dateUtil.createDateAsLong());
    
    stpStatusRepository.save(stpStatus);
    if(estado == 1){
      User user = userRepository.findOne(stpStatus.getUserId());
      transactionApplier.addAmount(user, stpStatus.getAmount());
      transactionLogService.createUserLog(null, stpStatus.getUserId(), null, stpStatus.getAmount(), stpStatus.getUuid(), TransactionType.STP_ROLLBACK);
    }
    
    transactionLogService.createStpLog(id, claveRastreo, estado, timestamp, TransactionType.STP_STATUS);
    
    return ResultType.SUCCESS;
  }
  
  public PaymentBean cashIn(Integer userId, String clabeOrdenante, String clabeBeneficiario, BigDecimal amount, String reference, String cancelUrl, String returnUrl){
    STPBean stpBean = new STPBean();
    TramaAccount tramaAccount = tramaAccountRepository.findByType(AdminAccountType.TRAMA);
    if (tramaAccount == null){
      throw new AccountNotFoundException(1);
    }
    
    if (!amountValidator.isValid(amount)){
    	throw new InvalidAmountException(amount);
    }
    
    if (stpCollaborator.getCentroDeCostos(clabeBeneficiario) == ApplicationState.STP_FREAKFUND_PREFIX){
    	User user = userRepository.findUserByClabe(clabeBeneficiario);
    	if (user == null){
    		throw new UserNotFoundException(1);
    	}
    	freakfundCashinStrategy.cashIn(user, amount, reference, null);
    }
    
    if (stpCollaborator.getCentroDeCostos(clabeBeneficiario) == ApplicationState.STP_INTEGRA_PREFIX){
    	IntegraUser integraUser = integraUserRepository.findByStpClabe(clabeBeneficiario);
    	if (integraUser == null){
    		throw new UserNotFoundException(1);
    	}
    	integraCashinStrategy.cashIn(integraUser, amount, reference, clabeOrdenante);
    }
    return stpBean;
  }
  
  public Iterable<BankCode> listBankCodes() {
    return bankCodeRepository.findAll();
  }

}
