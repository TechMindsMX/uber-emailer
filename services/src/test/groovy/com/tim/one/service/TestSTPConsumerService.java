package com.tim.one.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.lgec.enlacefi.spei.integration.h2h.OrdenPagoWS;
import com.tim.one.bean.AdminAccountType;
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
import com.tim.one.repository.BankCodeRepository;
import com.tim.one.repository.IntegraUserRepository;
import com.tim.one.repository.StpStatusRepository;
import com.tim.one.repository.TramaAccountRepository;
import com.tim.one.repository.UserAccountRepository;
import com.tim.one.repository.UserRepository;
import com.tim.one.service.impl.FreakfundCashinStrategy;
import com.tim.one.service.impl.STPConsumerServiceImpl;
import com.tim.one.service.impl.TransactionApplierImpl;
import com.tim.one.stp.collabotator.STPCollaborator;
import com.tim.one.stp.exception.SPEITransactionException;
import com.tim.one.stp.exception.STPResponseNotExists;
import com.tim.one.stp.exception.STPResponseNotValid;
import com.tim.one.stp.helper.STPHelper;
import com.tim.one.stp.service.STPService;
import com.tim.one.stp.state.ApplicationState;
import com.tim.one.util.DateUtil;
import com.tim.one.validator.AmountValidator;
import com.tim.one.validator.STPValidator;

public class TestSTPConsumerService {

  @InjectMocks
  private STPConsumerService stpConsumerService = new STPConsumerServiceImpl();
  
  @Mock
  private UserAccount userAccount;
  @Mock
  private User user;
  @Mock
  private STPHelper stpHelper;
  @Mock
  private STPCollaborator stpCollaborator;
  @Mock
  private STPConsumerCollaborator stpConsumerCollaborator;
  @Mock
  private OrdenPagoWS ordenPago;
  @Mock
  private TramaAccount bridgeAccount;
  @Mock
  private TramaAccount tramaAccount;
  @Mock
  private TransactionLogService transactionLogService;
  @Mock
  private CommonMessagePacker messagePacker;
  @Mock
  private AccountCollaborator accountCollaborator;
  @Mock
  private List<BankCode> bankCodes;
  @Mock
  private Properties properties;
  @Mock
  private AmountValidator amountValidator;
  @Mock
  private StpStatus stpStatus;
  @Mock
  private STPValidator stpValidator;
  @Mock
  private TransactionApplierImpl transactionApplier;
  @Mock
  private UserRepository userRepository;
  @Mock
  private DateUtil dateUtil;
  @Mock
  private TramaAccountRepository tramaAccountRepository;
  @Mock
  private BankCodeRepository bankCodeRepository;
  @Mock
  private StpStatusRepository stpStatusRepository;
  @Mock
  private UserAccountRepository userAccountRepository;
  @Mock
  private STPService stpService;
  @Mock
  private FreakfundCashinStrategy freakfundCashinStrategy;
  @Mock
  private IntegraUserRepository integraUserRepository;
  @Mock
  private IntegraUser integraUser;
  @Mock
  private CashinStrategy integraCashinStrategy;
  
  private Integer estado = 0;
  private Integer userId = 1;
  private Integer speiId = 2;
  private Integer bankCode = 3;
  private Integer claveRastreo = 4;

  private Long timestamp = 1L;
  private Long confirmationTimestamp = 2L;

  private String bankName = "bankName";
  private String email = "email";
  private String name = "name";
  private String clabe = "clabe";
  private String clabeBeneficiario = "clabeBeneficiario";
  private String reference = "reference";

  private BigDecimal amount = new BigDecimal("1000");

  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
  }
  
  @Test (expected=AccountNotFoundException.class)
  public void shouldNotRegistraOrdenDueToAccountNotExist() throws Exception {
    when(userRepository.findOne(userId)).thenReturn(user);
    stpConsumerService.cashOut(userId, amount);
  }
  
  @Test (expected=NonSufficientFundsException.class)
  public void shouldNotRegistraOrdenDueToNotSufficientFunds() throws Exception {
    BigDecimal userBalance = new BigDecimal("500");
    
    when(userAccountRepository.findByUser(user)).thenReturn(userAccount);
    when(userRepository.findOne(userId)).thenReturn(user);
    when(user.getBalance()).thenReturn(userBalance);
    
    stpConsumerService.cashOut(userId, amount);
  }
  
  @Test
  public void shouldRegistraOrden() throws Exception {
    setRegistraOrdenExpectations();
    when(user.getEmail()).thenReturn(email);
    when(user.getName()).thenReturn(name);
    when(userAccount.getBankCode()).thenReturn(bankCode);
    when(userAccount.getClabe()).thenReturn(clabe);
    when(accountCollaborator.getBankName(bankCode)).thenReturn(bankName);
    when(stpConsumerCollaborator.createOrdenStatus(speiId, userId, null, amount)).thenReturn(stpStatus);
    when(transactionApplier.hasFunds(user, amount)).thenReturn(true);
    when(stpService.registraOrden(name, email, bankCode, clabe, amount)).thenReturn(speiId);
    
    assertEquals(speiId, stpConsumerService.cashOut(userId, amount));
    
    verify(transactionApplier).substractAmount(user, amount);
    verify(transactionLogService).createUserLog(null, user.getId(), null, amount, null, TransactionType.CASH_OUT);
    verify(messagePacker).sendCashout(amount, bankName, email, speiId);
    verify(stpStatusRepository).save(stpStatus);
  }
  
  public void shouldNotRegistraOrdenDueToSTPError() throws Exception {
    BigDecimal userBalance = new BigDecimal("1500");
    
    setRegistraOrdenExpectations();
    when(stpCollaborator.createOrdenPago(name, email, bankCode, clabe, amount)).thenReturn(ordenPago);
    when(user.getBalance()).thenReturn(userBalance);
    when(transactionApplier.hasFunds(user, amount)).thenReturn(true);
    when(stpService.registraOrden(name, email, bankCode, clabe, amount)).thenThrow(new SPEITransactionException(clabe));
    
    stpConsumerService.cashOut(userId, amount);
    
    verify(transactionApplier, never()).substractAmount(user, amount);
    verify(transactionLogService, never()).createUserLog(null, user.getId(), null, amount, null, TransactionType.CASH_OUT);
    verify(messagePacker, never()).sendCashout(amount, bankName, email, speiId);
    verify(stpStatusRepository, never()).save(stpStatus);
  }
  
  @Test (expected=NonSufficientFundsException.class)
  public void shouldNotRegistraOrdenDueToNonSufficientFunds() throws Exception {
    setRegistraOrdenExpectations();
    when(transactionApplier.hasFunds(user, amount)).thenReturn(false);
    
    assertEquals(speiId, stpConsumerService.cashOut(userId, amount));
  }

  private void setRegistraOrdenExpectations() throws MalformedURLException {
    when(userAccountRepository.findByUser(user)).thenReturn(userAccount);
    when(userRepository.findOne(userId)).thenReturn(user);
  }
  
  @Test
  public void shouldListBankCodes() throws Exception {
    when(bankCodeRepository.findAll()).thenReturn(bankCodes);
    assertEquals(bankCodes, stpConsumerService.listBankCodes());
  }
  
  @Test
  public void shouldFreakfundCashIn() throws Exception {
    setCashInExpextations();
    when(userRepository.findUserByClabe(clabeBeneficiario)).thenReturn(user);
    when(stpConsumerCollaborator.getCentroDeCostos(clabeBeneficiario)).thenReturn(ApplicationState.STP_FREAKFUND_PREFIX);
    
    stpConsumerService.cashIn(null, clabe, clabeBeneficiario, amount, reference, null, null);
    
    verify(freakfundCashinStrategy).cashIn(user, amount, reference, null);
  }
  
  @Test
  public void shouldIntegraCashIn() throws Exception {
    setCashInExpextations();
    when(integraUserRepository.findByStpClabe(clabeBeneficiario)).thenReturn(integraUser);
    when(stpConsumerCollaborator.getCentroDeCostos(clabeBeneficiario)).thenReturn(ApplicationState.STP_INTEGRA_PREFIX);
    
    stpConsumerService.cashIn(null, clabe, clabeBeneficiario, amount, reference, null, null);
    
    verify(integraCashinStrategy).cashIn(integraUser, amount, reference, clabe);
  }

	private void setCashInExpextations() {
		when(tramaAccountRepository.findByType(AdminAccountType.TRAMA)).thenReturn(tramaAccount);
    when(amountValidator.isValid(amount)).thenReturn(true);
	}
  
  @Test (expected=AccountNotFoundException.class)
  public void shouldNotCashInDueToAccountNotFound() throws Exception {
    stpConsumerService.cashIn(null, clabe, clabeBeneficiario, amount, reference, null, null);
  }
  
  @Test (expected=UserNotFoundException.class)
  public void shouldNotCashInDueToUserNotFound() throws Exception {
    setCashInExpextations();
    when(stpConsumerCollaborator.getCentroDeCostos(clabeBeneficiario)).thenReturn(ApplicationState.STP_FREAKFUND_PREFIX);
    stpConsumerService.cashIn(null, clabe, clabeBeneficiario, amount, reference, null, null);
  }
  
  @Test (expected=InvalidAmountException.class)
  public void shouldNotCashInDueToInvalidAmount() throws Exception {
    when(tramaAccountRepository.findByType(AdminAccountType.TRAMA)).thenReturn(tramaAccount);
    when(userRepository.findUserByClabe(clabe)).thenReturn(user);
    when(amountValidator.isValid(amount)).thenReturn(false);
    
    stpConsumerService.cashIn(null, clabe, clabeBeneficiario, amount, reference, null, null);
  }
  
  @Test
  public void shouldConfirmaOrden() throws Exception {
    setStpStatusExpectations();
    
    stpConsumerService.confirmaOrden(speiId, claveRastreo, estado, timestamp);
    
    verifyStpStatusExpectations(estado);
    
    verify(stpStatusRepository).save(stpStatus);
    
    transactionLogService.createStpLog(speiId, claveRastreo, estado, timestamp, TransactionType.STP_STATUS);
    verify(transactionApplier, never()).addAmount(user, amount);
  }

  private void setStpStatusExpectations() {
    when(stpStatusRepository.findById(speiId)).thenReturn(stpStatus);
    when(stpValidator.isValid(estado)).thenReturn(true);
    when(dateUtil.createDateAsLong()).thenReturn(confirmationTimestamp);
    when(stpStatus.getUserId()).thenReturn(userId);
    when(stpStatus.getAmount()).thenReturn(amount);
  }
  
  @Test
  public void shouldConfirmaOrdenWithRollBack() throws Exception {
    Integer estado = 1;
    when(stpStatusRepository.findById(speiId)).thenReturn(stpStatus);
    when(stpValidator.isValid(estado)).thenReturn(true);
    when(dateUtil.createDateAsLong()).thenReturn(confirmationTimestamp);
    when(stpStatus.getUserId()).thenReturn(userId);
    when(stpStatus.getAmount()).thenReturn(amount);
    when(userRepository.findOne(userId)).thenReturn(user);
    
    stpConsumerService.confirmaOrden(speiId, claveRastreo, estado, timestamp);
    
    verifyStpStatusExpectations(estado);
    
    verify(stpStatusRepository).save(stpStatus);
    
    transactionLogService.createStpLog(speiId, claveRastreo, estado, timestamp, TransactionType.STP_STATUS);
    verify(transactionApplier).addAmount(user, amount);
    verify(transactionLogService).createUserLog(null, userId, null, amount, null, TransactionType.STP_ROLLBACK);
  }

  private void verifyStpStatusExpectations(Integer estado) {
    verify(stpStatus).setClaveRastreo(claveRastreo);
    verify(stpStatus).setEstado(estado);
    verify(stpStatus).setServerTimestamp(timestamp);
    verify(stpStatus).setConfirmationTimestamp(confirmationTimestamp);
  }
  
  @Test (expected=STPResponseNotExists.class)
  public void shouldNotConfirmaOrdenDueToSTPResponseNotExists() throws Exception {
    when(stpStatusRepository.findById(speiId)).thenReturn(null);
    stpConsumerService.confirmaOrden(speiId, claveRastreo, estado, timestamp);
  }
  
  @Test (expected=STPResponseNotValid.class)
  public void shouldNotConfirmaOrdenDueToSTPResponseNotValid() throws Exception {
    when(stpStatusRepository.findById(speiId)).thenReturn(stpStatus);
    when(stpValidator.isValid(estado)).thenReturn(false);
    stpConsumerService.confirmaOrden(speiId, claveRastreo, estado, timestamp);
  }

}
