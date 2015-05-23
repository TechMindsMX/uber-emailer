package com.tim.one.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.bean.BuyingSource;
import com.tim.one.bean.MessageType;
import com.tim.one.bean.PaymentType;
import com.tim.one.bean.ProjectTxType;
import com.tim.one.bean.PurchaseTransactionBean;
import com.tim.one.bean.ResultType;
import com.tim.one.bean.StatementType;
import com.tim.one.bean.TransactionBean;
import com.tim.one.bean.TransactionType;
import com.tim.one.bean.TransferUserLimitBean;
import com.tim.one.collaborator.ProjectCollectionInitializer;
import com.tim.one.collaborator.ProjectUnitSaleCollaborator;
import com.tim.one.collaborator.TransactionCollaborator;
import com.tim.one.exception.MaxTransferLimitExceedException;
import com.tim.one.exception.NonSufficientFundsException;
import com.tim.one.exception.NotBulkTransactionFoundExeption;
import com.tim.one.exception.NotValidParameterException;
import com.tim.one.exception.ProjectNotExistException;
import com.tim.one.exception.TransferUserLimitNotExistException;
import com.tim.one.exception.UserNotFoundException;
import com.tim.one.helper.TransactionHelper;
import com.tim.one.model.BulkUnitTx;
import com.tim.one.model.Project;
import com.tim.one.model.ProjectFinancialData;
import com.tim.one.model.ProjectProvider;
import com.tim.one.model.ProjectTx;
import com.tim.one.model.ProjectUnitSale;
import com.tim.one.model.ProviderTx;
import com.tim.one.model.TransferUserLimit;
import com.tim.one.model.UnitTx;
import com.tim.one.model.User;
import com.tim.one.model.UserTx;
import com.tim.one.packer.MessagePacker;
import com.tim.one.repository.BulkUnitTxRepository;
import com.tim.one.repository.ProjectFinancialDataRepository;
import com.tim.one.repository.ProjectProviderRepository;
import com.tim.one.repository.ProjectRepository;
import com.tim.one.repository.ProjectTxRepository;
import com.tim.one.repository.ProjectUnitSaleRepository;
import com.tim.one.repository.ProviderTxRepository;
import com.tim.one.repository.TransferUserLimitRepository;
import com.tim.one.repository.UnitTxRepository;
import com.tim.one.repository.UserRepository;
import com.tim.one.repository.UserTxRepository;
import com.tim.one.service.impl.TransactionApplierImpl;
import com.tim.one.service.impl.TransactionServiceImpl;
import com.tim.one.state.ApplicationState;
import com.tim.one.util.DateUtil;
import com.tim.one.validator.AmountValidator;

public class TestTransactionService {
	
	@InjectMocks
	private TransactionService transactionService = new TransactionServiceImpl();
	
	@Mock
	private UserRepository userRepository;
	@Mock
	private TransactionHelper transactionHelper;
	@Mock
	private UserTx userTx;
	@Mock
	private DateUtil dateUtil;
	@Mock
	private TransactionCollaborator transactionCollaborator;
	@Mock
	private UnitTx unitTx;
	@Mock
	private ProjectUnitSale projectUnitSale;
	@Mock
	private Project project;
	@Mock
	private BulkUnitTx bulkUnitTx;
	@Mock
	private ProviderTx providerTx;
	@Mock
	private ProjectProvider projectProvider;
	@Mock
	private User producer;
	@Mock
	private ProjectFinancialData projectFinancialData;
	@Mock
	private BigDecimal budget;
	@Mock
	private ProjectTx projectTx;
	@Mock
	private MessagePacker messagePacker;
	@Mock
	private User user;
	@Mock
	private User targetUser;
	@Mock
	private TransferUserLimit transferUserLimit;
	@Mock
	private TransferUserLimitBean transferUserLimitBean;
	@Mock
	private TransactionBean bean;
	@Mock
	private User provider;
	@Mock
	private TransactionLogService transactionLogService;
	@Mock
	private ProjectUnitSaleCollaborator projectUnitsaleCollaborator;
	@Mock
	private TransactionApplierImpl transactionApplier;
	@Mock
	private AmountValidator amountValidator;
	@Mock
	private Properties properties;
  @Mock
  private ProjectFinancialDataRepository projectFinancialDataRepository;
  @Mock
  private ProjectUnitSaleRepository projectUnitSaleRepository;
  @Mock
  private ProjectRepository projectRepository;
  @Mock
  private ProjectProviderRepository projectProviderRepository;
  @Mock
  private TransferUserLimitRepository transferUserLimitRepository;
  @Mock
  private BulkUnitTxRepository bulkUnitTxRepository;
  @Mock
  private UnitTxRepository unitTxRepository;
  @Mock
  private UserTxRepository userTxRepository;
  @Mock
  private ProviderTxRepository providerTxRepository;
  @Mock
  private ProjectTxRepository projectTxRepository;
  @Mock
  private ProjectCollectionInitializer projectCollectionInitializer;

	private Integer senderId = 1;
	private Integer receiverId = 2;
	private Integer userId = 3;
	private Integer projectId = 4;
	private Integer autorizadoStatus = 5;
	private Integer produccionStatus = 6;
	private Integer quantity = 7;
	private Integer units = 8;
	private Integer projectUnitSaleId = 9;
	private Integer bulkUnitTxId = 10;
	private Integer producerId = 11;
	private Integer providerId = 12;
	private Integer userTxId = 13;
	private Integer destinationId = 14;
	private Integer transactionId = 15;
	private Integer transferUserLimitId = 16;
	private Integer otherUserId = 17;
	private Integer bulkId = 18;
	private Integer providerTxId = 19;
	
	private Long timestamp = 1L;
	private Long otherTimestamp = 2L;
	private Long startDate = 3L;
	private Long endDate = 4L;

	private String name = "name";
	private String email = "email";
	private String senderName = "senderName";
	private String senderEmail = "senderEmail";
	private String receiverName = "name";
	private String receiverEmail = "email";
	private String account = "account";
	private String section = "section";
	private String projectName = "projectName";
	private String autorizadoStatusAsString = "5";

	private BigDecimal amount = new BigDecimal("500");
	private BigDecimal zero = new BigDecimal("0");
	private BigDecimal unitSale = new BigDecimal("100");
	private BigDecimal advance  = new BigDecimal("1000");
	private BigDecimal settlement = new BigDecimal("1500");
	private BigDecimal balance = new BigDecimal("50000");
	private BigDecimal totalExpected = new BigDecimal("700");
	private BigDecimal projectBalance = new BigDecimal("80000");
	private BigDecimal providerBalance = new BigDecimal("9000");
	
	private User sender = new User();
	private User receiver = new User();
	private TransactionBean transactionBean = new TransactionBean();
	private TransactionBean otherTransactionBean = new TransactionBean();

	private TransactionType transactionType = TransactionType.FUNDING;

	private Map<String, String> params = new HashMap<String, String>();
	private Set<UnitTx> unitsAsSet = new HashSet<UnitTx>();
	private List<UnitTx> unitsAsList = new ArrayList<UnitTx>();
	private List<TransferUserLimit> transferUserLimits = new ArrayList<TransferUserLimit>();
	private List<UserTx> userTxs = new ArrayList<UserTx>();
	private List<TransactionBean> transactionBeans = new ArrayList<TransactionBean>();
	private List<ProviderTx> providerTxs = new ArrayList<ProviderTx>();
	private List<ProjectTx> projectTxs = new ArrayList<ProjectTx>();
	private Set<ProjectUnitSale> projectUnitSales = new HashSet<ProjectUnitSale>();

	@Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test(expected=UserNotFoundException.class)
	public void shouldNotTransferFundsDueToNoEmisor() throws Exception {
		when(userRepository.findOne(senderId)).thenReturn(null);
		when(userRepository.findOne(receiverId)).thenReturn(receiver);
		
		transactionService.transferFunds(senderId, receiverId, amount);
	}
	
	@Test(expected=UserNotFoundException.class)
	public void shouldNotTransferFundsDueToNoReceiver() throws Exception {
		when(userRepository.findOne(senderId)).thenReturn(sender);
		when(userRepository.findOne(receiverId)).thenReturn(null);
		
		transactionService.transferFunds(senderId, receiverId, amount);
	}
	
	@Test(expected=NonSufficientFundsException.class)
	public void shouldNotTransferFundsDueToNotInsufficientFunds() throws Exception {
		when(userRepository.findOne(senderId)).thenReturn(sender);
		when(userRepository.findOne(receiverId)).thenReturn(receiver);
		when(transactionApplier.hasFunds(sender, amount)).thenReturn(false);
		
		transactionService.transferFunds(senderId, receiverId, amount);
	}
	
	@Test(expected=MaxTransferLimitExceedException.class)
	public void shouldNotTransferFundsDueToExceedMaxTransfer() throws Exception {
		BigDecimal maxAmount = new BigDecimal("100");
		
		when(userRepository.findOne(senderId)).thenReturn(sender);
		when(userRepository.findOne(receiverId)).thenReturn(receiver);
		when(transactionApplier.hasFunds(sender, amount)).thenReturn(true);
		
		sender.setId(senderId);
		receiver.setId(receiverId);
		
		when(transactionCollaborator.getTransferUserLimit(senderId, receiverId)).thenReturn(transferUserLimit);
		when(transferUserLimit.getAmount()).thenReturn(maxAmount);
		
		transactionService.transferFunds(senderId, receiverId, amount);
	}
	
	@Test(expected=NotValidParameterException.class)
	public void shouldNotTransferFundsDueToNotValidParamter() throws Exception {
		transactionService.transferFunds(senderId, senderId, amount);
	}
	
	@Test
	public void shouldTransferFunds() throws Exception {
		BigDecimal balance = new BigDecimal("1000");
		BigDecimal maxAmount = new BigDecimal("2000");
		
		when(userRepository.findOne(senderId)).thenReturn(sender);
		when(userRepository.findOne(receiverId)).thenReturn(receiver);
		when(userTx.getTimestamp()).thenReturn(timestamp);
		when(transactionLogService.createUserLog(null, receiverId, senderId, amount, null, TransactionType.TRANSFER)).thenReturn(userTxId);
		
		sender.setId(senderId);
		sender.setName(senderName);
		sender.setEmail(senderEmail);
		
		receiver.setId(receiverId);
		sender.setBalance(balance);
		receiver.setName(receiverName);
		receiver.setEmail(receiverEmail);
		
		when(dateUtil.createDateAsLong()).thenReturn(timestamp);
		when(transactionCollaborator.getTransferUserLimit(senderId, receiverId)).thenReturn(transferUserLimit);
		when(transferUserLimit.getAmount()).thenReturn(maxAmount);
		when(transactionApplier.hasFunds(sender, amount)).thenReturn(true);
		
		transactionService.transferFunds(senderId, receiverId, amount);
		
		verify(transactionApplier).addAmount(receiver, amount);
    verify(transactionApplier).substractAmount(sender, amount);
    
		verify(messagePacker).sendAbonoCuenta(amount, senderName, receiverEmail, userTxId, MessageType.TRASPASO_CUENTA_BENEFICIARIO);
		verify(messagePacker).sendAbonoCuenta(amount, receiverName, senderEmail, userTxId, MessageType.TRASPASO_CUENTA_ORDENANTE);
	}
	
	@Test
	public void shouldConsumeUnitsAsFunding() throws Exception {
		setConsumeUnitExpectations();
		
		when(project.getStatus()).thenReturn(autorizadoStatus);
		when(projectUnitSale.getUnitSale()).thenReturn(unitSale);
		
		transactionService.createUnits(userId, projectId, params, BuyingSource.FREAKFUND);
		
		verifyConsumingUnitsExpectations();

		verify(transactionLogService).createUnitLog(projectUnitSaleId, quantity, userId, bulkUnitTx, TransactionType.FUNDING);
		verify(transactionLogService).createProjectLog(projectId, userId, totalExpected, ProjectTxType.FUNDING);
		verify(transactionCollaborator).verifyBreakeven(project, projectFinancialData);
		
		verify(projectFinancialData).setBalance(balance.add(totalExpected));
	}
	
	@Test (expected=ProjectNotExistException.class)
  public void shouldNotConsumeUnitsAsFundingDueToNoProjectExist() throws Exception {
    transactionService.createUnits(userId, projectId, params, BuyingSource.FREAKFUND);
  }

	private void setConsumeUnitExpectations() {
		when(properties.getProperty(ApplicationState.AUTORIZADO_STATUS)).thenReturn(autorizadoStatusAsString);
		
		params.put(projectUnitSaleId.toString(), quantity.toString());
		when(dateUtil.createDateAsLong()).thenReturn(timestamp);
		
		when(projectFinancialData.getProjectUnitSales()).thenReturn(projectUnitSales);
		when(projectUnitsaleCollaborator.getProjectUnitsale(projectUnitSaleId, projectUnitSales)).thenReturn(projectUnitSale);
		when(bulkUnitTx.getId()).thenReturn(transactionId);
		
		when(projectUnitSale.getUnit()).thenReturn(units);
		when(projectUnitSale.getId()).thenReturn(projectUnitSaleId);
		when(projectRepository.findOne(projectId)).thenReturn(project);
		when(transactionHelper.createUnits()).thenReturn(unitsAsSet);
		when(transactionHelper.createBulkUnit()).thenReturn(bulkUnitTx);
		when(project.getProjectFinancialData()).thenReturn(projectFinancialData);
		when(projectFinancialDataRepository.findOne(projectId)).thenReturn(projectFinancialData);
		when(projectFinancialData.getBalance()).thenReturn(balance);
	}
	
	@Test
	public void shouldConsumeUnitsAsInvestment() throws Exception {
		setConsumeUnitExpectations();
		
		when(project.getStatus()).thenReturn(produccionStatus);
		when(projectUnitSale.getUnitSale()).thenReturn(unitSale);
		
		transactionService.createUnits(userId, projectId, params, BuyingSource.FREAKFUND);
		
		verifyConsumingUnitsExpectations();

		verify(transactionLogService).createUnitLog(projectUnitSaleId, quantity, userId, bulkUnitTx, TransactionType.INVESTMENT);
		verify(transactionLogService).createProjectLog(projectId, userId, totalExpected, ProjectTxType.INVESTMENT);
		verify(projectFinancialData).setBalance(balance.add(totalExpected));
	}

	private void verifyConsumingUnitsExpectations() {
		verify(projectUnitSale).setUnit(1);
		verify(projectUnitSaleRepository).save(projectUnitSale);
		
		assertEquals(1, unitsAsSet.size());
		verify(bulkUnitTx).setUnits(unitsAsSet);
		
		verify(projectFinancialDataRepository).save(projectFinancialData);
		
		verify(messagePacker).sendInversionFinanciamiento(userId, project, unitsAsSet, transactionId);
	}
	
	@Test
	public void shouldGetFundedAmount() throws Exception {
		BigDecimal expectedResult = new BigDecimal("700");
		
		unitsAsList.add(unitTx);
		when(unitTxRepository.findUnitsByTypeAndUserId(TransactionType.FUNDING, userId)).thenReturn(unitsAsList);
		when(unitTx.getProjectUnitSaleId()).thenReturn(projectUnitSaleId);
		when(projectUnitSaleRepository.findOne(projectUnitSaleId)).thenReturn(projectUnitSale);
		when(projectUnitSale.getProjectFinancialData()).thenReturn(projectFinancialData);
		when(projectFinancialData.getProject()).thenReturn(project);
		when(projectUnitSale.getUnitSale()).thenReturn(unitSale);
		when(unitTx.getQuantity()).thenReturn(quantity);
		
		assertEquals(expectedResult, transactionService.getAmountByUser(TransactionType.FUNDING, project, userId));
	}
	
	@Test
	public void shouldGetTransactions() throws Exception {
		unitsAsSet.add(unitTx);
		when(bulkUnitTxRepository.findById(bulkUnitTxId)).thenReturn(bulkUnitTx);
		when(bulkUnitTx.getUnits()).thenReturn(unitsAsSet);
		when(unitTx.getProjectUnitSaleId()).thenReturn(projectUnitSaleId);
		when(projectUnitSaleRepository.findOne(projectUnitSaleId)).thenReturn(projectUnitSale);
		when(projectUnitSale.getProjectFinancialData()).thenReturn(projectFinancialData);
		when(projectFinancialData.getId()).thenReturn(projectId);
		when(projectUnitSale.getSection()).thenReturn(section);
		when(projectUnitSale.getUnitSale()).thenReturn(unitSale);
		when(unitTx.getQuantity()).thenReturn(quantity);
		
		List<PurchaseTransactionBean> result = transactionService.getTransactions(bulkUnitTxId);
		
		assertEquals(1, result.size());
		assertEquals(projectId, result.get(0).getProjectId());
		assertEquals(section, result.get(0).getSection());
		assertEquals(unitSale, result.get(0).getUnitSale());
		assertEquals(quantity, result.get(0).getQuantity());
	}
	
	@Test (expected=NotBulkTransactionFoundExeption.class)
	public void shouldNotGetTransactions() throws Exception {
		assertEquals(unitsAsSet, transactionService.getTransactions(bulkUnitTxId));
	}
	
	@Test
	public void shouldSaveAdvanceProviderPartnership() throws Exception {
		setProviderPartnershipExpectations();

		when(projectProvider.getAdvanceQuantity()).thenReturn(advance);
		when(projectProvider.getAdvancePaidDate()).thenReturn(null);
		
		transactionService.createProviderPartnership(providerId, projectId, PaymentType.ADVANCE);
		
		verify(projectProvider).setAdvanceFundingDate(timestamp);
		
		verify(transactionLogService).createProviderLog(providerId, projectId, advance, PaymentType.ADVANCE, TransactionType.FUNDING);
		verify(transactionCollaborator).addFunds(projectId, advance);
		verify(projectProviderRepository).save(projectProvider);
	}

	private void setProviderPartnershipExpectations() {
	  when(projectRepository.findOne(projectId)).thenReturn(project);
    when(userRepository.findOne(providerId)).thenReturn(user);
		when(transactionHelper.createProviderTx()).thenReturn(providerTx);
		when(dateUtil.createDateAsLong()).thenReturn(timestamp);
		when(projectProviderRepository.findByProjectAndProviderId(project, providerId)).thenReturn(projectProvider);
		when(projectProvider.getProject()).thenReturn(project);
	}
	
	@Test
	public void shouldSaveSettlementProviderPartnership() throws Exception {
		setProviderPartnershipExpectations();

		when(projectProvider.getSettlementQuantity()).thenReturn(settlement);
		when(projectProvider.getSettlementPaidDate()).thenReturn(null);
		
		transactionService.createProviderPartnership(providerId, projectId, PaymentType.SETTLEMENT);
		
		verify(projectProvider).setSettlementFundingDate(timestamp);
		verify(transactionCollaborator).addFunds(projectId, settlement);
		verify(projectProviderRepository).save(projectProvider);
		verify(transactionLogService).createProviderLog(providerId, projectId, settlement, PaymentType.SETTLEMENT, TransactionType.FUNDING);
	}
	
	@Test
	public void shouldSaveProviderPartnership() throws Exception {
		BigDecimal expectedPayment = new BigDecimal("2500");
		setProviderPartnershipExpectations();

		when(projectProvider.getAdvanceQuantity()).thenReturn(advance);
		when(projectProvider.getSettlementQuantity()).thenReturn(settlement);
		when(projectProvider.getAdvancePaidDate()).thenReturn(null);
		when(projectProvider.getSettlementPaidDate()).thenReturn(null);
		
		transactionService.createProviderPartnership(providerId, projectId, PaymentType.BOUTH);
		
		verify(projectProvider).setAdvanceFundingDate(timestamp);
		verify(projectProvider).setSettlementFundingDate(timestamp);
		
		verify(transactionCollaborator).addFunds(projectId, advance.add(settlement));
		verify(projectProviderRepository).save(projectProvider);
		verify(transactionLogService).createProviderLog(providerId, projectId, expectedPayment, PaymentType.BOUTH, TransactionType.FUNDING);
		verify(transactionLogService).createProjectLog(projectId, providerId, expectedPayment, ProjectTxType.PROVIDER_PARTNERSHIP);
	}
	
	@Test
	public void shouldSaveProducerPartnership() throws Exception {
	  when(userRepository.findOne(producerId)).thenReturn(producer);
		when(projectRepository.findOne(projectId)).thenReturn(project);
		when(project.getUser()).thenReturn(producer);
		when(producer.getId()).thenReturn(producerId);
		when(projectFinancialDataRepository.findOne(projectId)).thenReturn(projectFinancialData);
		when(projectFinancialData.getBudget()).thenReturn(budget);
		when(transactionCollaborator.getProducerRevenue(budget)).thenReturn(amount);
		when(transactionHelper.createProjectTx()).thenReturn(projectTx);
		
		when(projectFinancialData.getBalance()).thenReturn(balance);
		
		assertEquals(ResultType.SUCCESS, transactionService.createProducerPartnership(projectId));
		
		verify(transactionLogService).createProjectLog(projectId, producerId, amount, ProjectTxType.PRODUCER_PARTNERSHIP);
		verify(transactionApplier).addAmount(projectFinancialData, amount);
	}
	
	@Test(expected=UserNotFoundException.class)
	public void shouldNotSaveProducerPartnership() throws Exception {
		when(projectRepository.findOne(projectId)).thenReturn(project);
		when(project.getUser()).thenReturn(producer);
    when(producer.getId()).thenReturn(userId);
		when(userRepository.findOne(userId)).thenReturn(null);
		
		transactionService.createProducerPartnership(projectId);
	}
	
	@Test(expected=ProjectNotExistException.class)
  public void shouldNotSaveProducerPartnershipDueToProjectNotFund() throws Exception {
    transactionService.createProducerPartnership(projectId);
  }
	
	@Test
	public void shouldSaveProducerFunding() throws Exception {
		BigDecimal producerExpectedResult = new BigDecimal("49500");
		when(projectRepository.findOne(projectId)).thenReturn(project);
		when(project.getUser()).thenReturn(producer);
    when(producer.getId()).thenReturn(producerId);
		when(userRepository.findOne(producerId)).thenReturn(producer);
		when(producer.getBalance()).thenReturn(balance);
		
		when(projectFinancialDataRepository.findOne(projectId)).thenReturn(projectFinancialData);
		when(projectFinancialData.getBalance()).thenReturn(balance);
		
		transactionService.createProducerFunding(projectId, amount);
		
		verify(producer).setBalance(producerExpectedResult);
		verify(userRepository).save(producer);
		
		verify(transactionApplier).addAmount(projectFinancialData, amount);
		verify(transactionLogService).createProjectLog(projectId, producerId, amount, ProjectTxType.PRODUCER_FUNDING);
	}
	
	@Test (expected=UserNotFoundException.class)
  public void shouldNotSaveProducerFundingDueToNotUserFound() throws Exception {
    when(projectRepository.findOne(projectId)).thenReturn(project);
    when(project.getUser()).thenReturn(producer);
    when(producer.getId()).thenReturn(producerId);
    when(userRepository.findOne(producerId)).thenReturn(null);
    
    transactionService.createProducerFunding(projectId, amount);
  }
	
	@Test (expected=ProjectNotExistException.class)
	public void shouldNotSaveProducerFundingDueToNotProjectFound() throws Exception {
		transactionService.createProducerFunding(projectId, amount);
	}
	
	@Test (expected=NonSufficientFundsException.class)
	public void shouldNotSaveProducerFundingDueToNonSufficientFunds() throws Exception {
		BigDecimal hugeAmount = new BigDecimal("50500");
		
		when(projectRepository.findOne(projectId)).thenReturn(project);
		when(project.getUser()).thenReturn(producer);
    when(producer.getId()).thenReturn(producerId);
		when(userRepository.findOne(producerId)).thenReturn(producer);
		
		when(producer.getBalance()).thenReturn(balance);
		
		transactionService.createProducerFunding(projectId, hugeAmount);
	}
	
	@Test (expected=UserNotFoundException.class)
	public void shouldNotSetMaxAmountToTransferDueToNoUser() throws Exception {
		when(userRepository.findOne(userId)).thenReturn(null);
		when(userRepository.findOne(destinationId)).thenReturn(targetUser);
		
		transactionService.measureAmountToTransfer(userId, destinationId, amount);
	}
	
	@Test (expected=UserNotFoundException.class)
	public void shouldNotSetMaxAmountToTransferDueToNoTagetUser() throws Exception {
		when(userRepository.findOne(userId)).thenReturn(user);
		when(userRepository.findOne(destinationId)).thenReturn(null);
		
		transactionService.measureAmountToTransfer(userId, destinationId, amount);
	}
	
	@Test (expected=NotValidParameterException.class)
	public void shouldNotSetMaxAmountToTransferDueToNotValidParameter() throws Exception {
		transactionService.measureAmountToTransfer(userId, userId, amount);
	}
	

	@Test (expected=NotValidParameterException.class)
	  public void shouldNotSetMaxAmountToTransferDueToNotValidAmount() throws Exception {
  	  when(userRepository.findOne(userId)).thenReturn(user);
      when(userRepository.findOne(destinationId)).thenReturn(targetUser);
      when(amountValidator.isValid(amount)).thenReturn(false);
	    
	    transactionService.measureAmountToTransfer(userId, destinationId, zero);
	}

	@Test
	public void shouldSetMaxAmountToTransfer() throws Exception {
		when(userRepository.findOne(userId)).thenReturn(user);
		when(userRepository.findOne(destinationId)).thenReturn(targetUser);
		when(transactionCollaborator.getTransferUserLimit(userId, destinationId)).thenReturn(transferUserLimit);
		when(amountValidator.isValid(amount)).thenReturn(true);
		
		transactionService.measureAmountToTransfer(userId, destinationId, amount);
		
		verify(transferUserLimit).setUserId(userId);
		verify(transferUserLimit).setDestinationId(destinationId);
		verify(transferUserLimit).setAmount(amount);
		verify(transferUserLimit).setTimestamp(dateUtil.createDateAsLong());
		verify(transactionCollaborator).save(transferUserLimit);
	}
	
	@Test
	public void shouldGetLimitAmountsToTransfer() throws Exception {
		transferUserLimits.add(transferUserLimit);
		
		when(transferUserLimitRepository.findByUserId(userId)).thenReturn(transferUserLimits);
		when(transactionHelper.createTransferUserLimitBean()).thenReturn(transferUserLimitBean);
		when(transferUserLimit.getDestinationId()).thenReturn(destinationId);
		when(transferUserLimit.getAmount()).thenReturn(amount);
		when(transferUserLimit.getId()).thenReturn(transferUserLimitId);
		when(userRepository.findOne(destinationId)).thenReturn(user);
		when(user.getName()).thenReturn(name);
		when(user.getEmail()).thenReturn(email);
		when(user.getAccount()).thenReturn(account);
		
		List<TransferUserLimitBean> result = transactionService.getLimitAmountsToTransfer(userId);
		
		verify(transferUserLimitBean).setDestinationId(destinationId);
		verify(transferUserLimitBean).setAccount(account);
		verify(transferUserLimitBean).setAmount(amount);
		verify(transferUserLimitBean).setName(name);
		verify(transferUserLimitBean).setEmail(email);
		verify(transferUserLimitBean).setId(transferUserLimitId);
		assertEquals(transferUserLimitBean, result.get(0));
	}
	
	@Test
	public void shouldDeleteLimitAmountToTransfer() throws Exception {
	  when(transferUserLimitRepository.findByUserIdAndDestinationId(userId, destinationId)).thenReturn(transferUserLimit);
		transactionService.changeLimitAmountToTransfer(userId, destinationId);
		verify(transferUserLimitRepository).delete(transferUserLimit);
	}
	
	@Test(expected=TransferUserLimitNotExistException.class)
  public void shouldNotDeleteLimitAmountToTransfer() throws Exception {
    transactionService.changeLimitAmountToTransfer(userId, destinationId);
    verify(transferUserLimitRepository, never()).delete(transferUserLimit);
  }
	
	@Test
	public void shouldGetUserTransaction() throws Exception {
		when(userTxRepository.findById(transactionId)).thenReturn(userTx);
		assertEquals(userTx, transactionService.getUserTransaction(transactionId));
	}
	
	@Test
	public void shouldGetUserStatementCredit() throws Exception {
		setUserStatementExpectations();

		when(userTx.getType()).thenReturn(TransactionType.CREDIT);
		
		List<TransactionBean> result = transactionService.getUserTransactions(userId, startDate, endDate);
		
		verifyUserStatementExpectations(result);
		
		assertEquals(TransactionType.CREDIT.toString(), transactionBean.getDescription());
		assertEquals(StatementType.CREDIT, transactionBean.getType());
	}
	
	@Test
	public void shouldGetUserStatementCreditByTrasfer() throws Exception {
		setUserStatementExpectations();
		
		when(userTx.getReceiverId()).thenReturn(userId);
		when(userTx.getType()).thenReturn(TransactionType.TRANSFER);
		
		List<TransactionBean> result = transactionService.getUserTransactions(userId, startDate, endDate);
		
		verifyUserStatementExpectations(result);

		assertEquals(TransactionType.TRANSFER.toString(), transactionBean.getDescription());
		assertEquals(StatementType.CREDIT, transactionBean.getType());
	}
	
	@Test
	public void shouldGetUserStatementCreditReturnFunds() throws Exception {
		setUserStatementExpectations();

		when(userTx.getType()).thenReturn(TransactionType.RETURN_FUNDS);
		
		List<TransactionBean> result = transactionService.getUserTransactions(userId, startDate, endDate);
		
		verifyUserStatementExpectations(result);
		
		assertEquals(TransactionType.RETURN_FUNDS.toString(), transactionBean.getDescription());
		assertEquals(StatementType.CREDIT, transactionBean.getType());
	}
	
	@Test
	public void shouldGetUserStatementCreditByRDF() throws Exception {
		setUserStatementExpectations();
		
		when(userTx.getReceiverId()).thenReturn(otherUserId);
		when(userTx.getType()).thenReturn(TransactionType.RDF);
		when(userTx.getProjectId()).thenReturn(projectId);
		when(projectRepository.findOne(projectId)).thenReturn(project);
		when(project.getName()).thenReturn(projectName);
		
		List<TransactionBean> result = transactionService.getUserTransactions(userId, startDate, endDate);
		
		verifyUserStatementExpectations(result);

		assertEquals(TransactionType.RDF.toString(), transactionBean.getDescription());
		assertEquals(StatementType.CREDIT, transactionBean.getType());
		assertEquals(projectName, transactionBean.getProjectName());
	}
	
	@Test
	public void shouldGetUserStatementCreditByRDI() throws Exception {
		setUserStatementExpectations();
		
		when(userTx.getReceiverId()).thenReturn(otherUserId);
		when(userTx.getType()).thenReturn(TransactionType.RDI);
		when(userTx.getProjectId()).thenReturn(projectId);
		when(projectRepository.findOne(projectId)).thenReturn(project);
		when(project.getName()).thenReturn(projectName);
		
		List<TransactionBean> result = transactionService.getUserTransactions(userId, startDate, endDate);
		
		verifyUserStatementExpectations(result);

		assertEquals(TransactionType.RDI.toString(), transactionBean.getDescription());
		assertEquals(StatementType.CREDIT, transactionBean.getType());
		assertEquals(projectName, transactionBean.getProjectName());
	}
	
	@Test
	public void shouldGetUserStatementCreditProducerPayment() throws Exception {
		setUserStatementExpectations();
		setProjectExpectations();

		when(userTx.getType()).thenReturn(TransactionType.PRODUCER_PAYMENT);
		
		List<TransactionBean> result = transactionService.getUserTransactions(userId, startDate, endDate);
		
		verifyUserStatementExpectations(result);
		
		assertEquals(TransactionType.PRODUCER_PAYMENT.toString(), transactionBean.getDescription());
		assertEquals(StatementType.CREDIT, transactionBean.getType());
	}
	
	@Test
	public void shouldGetUserStatementCreditProviderReturnOfCapital() throws Exception {
		setUserStatementExpectations();
		setProjectExpectations();

		when(userTx.getType()).thenReturn(TransactionType.PROVIDER_RETURN_CAPITAL);
		
		List<TransactionBean> result = transactionService.getUserTransactions(userId, startDate, endDate);
		
		verifyUserStatementExpectations(result);
		
		assertEquals(TransactionType.PROVIDER_RETURN_CAPITAL.toString(), transactionBean.getDescription());
		assertEquals(StatementType.CREDIT, transactionBean.getType());
	}
	
	@Test
	public void shouldGetUserStatementCreditByTrasferFromTRama() throws Exception {
		setUserStatementExpectations();
		
		when(userTx.getReceiverId()).thenReturn(otherUserId);
		when(userTx.getType()).thenReturn(TransactionType.TRANSFER_FROM_TRAMA);
		
		List<TransactionBean> result = transactionService.getUserTransactions(userId, startDate, endDate);
		
		verifyUserStatementExpectations(result);

		assertEquals(TransactionType.TRANSFER_FROM_TRAMA.toString(), transactionBean.getDescription());
		assertEquals(StatementType.CREDIT, transactionBean.getType());
	}

	private void setProjectExpectations() {
		when(userTx.getProjectId()).thenReturn(projectId);
		when(projectRepository.findOne(projectId)).thenReturn(project);
		when(project.getName()).thenReturn(projectName);
	}

	private void verifyUserStatementExpectations(List<TransactionBean> result) {
		assertEquals(1, result.size());
		assertEquals(transactionBean, result.get(0));
		assertEquals(ApplicationState.USER_TX_PREFIX + transactionId, transactionBean.getReference());
		assertEquals(timestamp, transactionBean.getTimestamp());
		assertEquals(amount, transactionBean.getAmount());
	}

	private void setUserStatementExpectations() {
		userTxs.add(userTx);
		when(userTxRepository.findByUserAndDate(userId, startDate, endDate)).thenReturn(userTxs);
		when(transactionHelper.createTransactionBean()).thenReturn(transactionBean);
		when(userTx.getId()).thenReturn(transactionId);
		when(userTx.getTimestamp()).thenReturn(timestamp);
		when(userTx.getAmount()).thenReturn(amount);
	}
	
	@Test
	public void shouldSumAmount() throws Exception {
		BigDecimal expectedBalance = new BigDecimal("2").multiply(amount);
		transactionBeans.add(transactionBean);
		transactionBeans.add(otherTransactionBean);
		
		transactionBean.setTimestamp(timestamp);
		otherTransactionBean.setTimestamp(otherTimestamp);
		transactionBean.setType(StatementType.CREDIT);
		otherTransactionBean.setType(StatementType.CREDIT);
		transactionBean.setAmount(amount);
		otherTransactionBean.setAmount(amount);
		
		List<TransactionBean> result = transactionService.setBalances(transactionBeans, zero);
		
		assertEquals(2, result.size());
		assertEquals(transactionBean, result.get(0));
		assertEquals(otherTransactionBean, result.get(1));
		assertEquals(amount, result.get(0).getBalance());
		assertEquals(expectedBalance, result.get(1).getBalance());
	}
	
	@Test
	public void shouldSubstractAmount() throws Exception {
		transactionBeans.add(transactionBean);
		transactionBeans.add(otherTransactionBean);
		
		transactionBean.setTimestamp(otherTimestamp);
		otherTransactionBean.setTimestamp(timestamp);
		transactionBean.setType(StatementType.DEBIT);
		otherTransactionBean.setType(StatementType.CREDIT);
		transactionBean.setAmount(amount);
		otherTransactionBean.setAmount(amount);
		
		List<TransactionBean> result = transactionService.setBalances(transactionBeans, zero);
		
		assertEquals(2, result.size());
		assertEquals(transactionBean, result.get(1));
		assertEquals(otherTransactionBean, result.get(0));
		assertEquals(amount, result.get(0).getBalance());
		assertEquals(zero, result.get(1).getBalance());
	}
	
	@Test
	public void shouldGetBulkTransactions() throws Exception {
		BigDecimal expectedAmount = new BigDecimal("700");
		unitsAsList.add(unitTx);
		
		when(unitTxRepository.findByUserAndDate(userId, startDate, endDate)).thenReturn(unitsAsList);
		when(bulkUnitTxRepository.findById(bulkId)).thenReturn(bulkUnitTx);
		when(transactionHelper.createTransactionBean()).thenReturn(transactionBean);
		when(unitTx.getBulk()).thenReturn(bulkUnitTx);
		when(bulkUnitTx.getId()).thenReturn(bulkId);
		when(bulkUnitTx.getUnits()).thenReturn(unitsAsSet);
		when(transactionCollaborator.getDescription(unitsAsSet)).thenReturn(transactionType);
		when(bulkUnitTx.getTimestamp()).thenReturn(timestamp);
		when(transactionCollaborator.getAmount(unitsAsSet)).thenReturn(expectedAmount);
		when(unitTx.getProjectUnitSaleId()).thenReturn(projectUnitSaleId);
		when(bulkUnitTx.getProjectName()).thenReturn(projectName);

		List<TransactionBean> result = transactionService.getUnitTransactions(userId, startDate, endDate);
		
		verify(transactionCollaborator).setProjectName(bulkUnitTx, projectUnitSaleId);
		assertEquals(1, result.size());
		assertEquals(transactionBean, result.get(0));
		assertEquals(ApplicationState.UNIT_TX_PREFIX + bulkId, transactionBean.getReference());
		assertEquals(transactionType.toString(), transactionBean.getDescription());
		assertEquals(timestamp, transactionBean.getTimestamp());
		assertEquals(expectedAmount, transactionBean.getAmount());
		assertEquals(bulkId, transactionBean.getBulkId());
		assertEquals(projectName, transactionBean.getProjectName());
	}
	
	@Test
	public void shouldGetUnitTransactions() throws Exception {
		BigDecimal expectedAmount = new BigDecimal("700");
		unitsAsSet.add(unitTx);
		
		when(bulkUnitTxRepository.findById(bulkId)).thenReturn(bulkUnitTx);
		when(bulkUnitTx.getUnits()).thenReturn(unitsAsSet);
		when(transactionHelper.createTransactionBean()).thenReturn(transactionBean);
		when(unitTx.getId()).thenReturn(transactionId);
		when(unitTx.getType()).thenReturn(TransactionType.FUNDING);
		when(unitTx.getTimestamp()).thenReturn(timestamp);
		when(unitTx.getProjectUnitSaleId()).thenReturn(projectUnitSaleId);
		when(projectUnitSaleRepository.findOne(projectUnitSaleId)).thenReturn(projectUnitSale);
		when(unitTx.getQuantity()).thenReturn(quantity);
		when(projectUnitSale.getUnitSale()).thenReturn(unitSale);
		
		List<TransactionBean> result = transactionService.getBulkTransactions(bulkId);
		
		assertEquals(1, result.size());
		assertEquals(transactionBean, result.get(0));
		assertEquals(ApplicationState.UNIT_TX_PREFIX + transactionId, transactionBean.getReference());
		assertEquals(timestamp, transactionBean.getTimestamp());
		assertEquals(expectedAmount, transactionBean.getAmount());
	}
	
	@Test
	public void shouldGetProviderDebitTransactions() throws Exception {
		providerTxs.add(providerTx);
		
		setProviderTxsExpectations();
		when(providerTx.getType()).thenReturn(TransactionType.FUNDING);
		
		List<TransactionBean> result = transactionService.getProviderTransactions(userId, startDate, endDate);
		
		TransactionBean bean = verifyProviderTxsExpectations(result);
		assertEquals(TransactionType.FUNDING.toString(), bean.getDescription());
		assertEquals(StatementType.DEBIT, bean.getType());
	}
	
	@Test
	public void shouldGetProviderCreditTransactions() throws Exception {
		providerTxs.add(providerTx);
		
		setProviderTxsExpectations();
		when(providerTx.getType()).thenReturn(TransactionType.PAYMENT);
		
		List<TransactionBean> result = transactionService.getProviderTransactions(userId, startDate, endDate);
		
		TransactionBean bean = verifyProviderTxsExpectations(result);
		assertEquals(TransactionType.PAYMENT.toString(), bean.getDescription());
		assertEquals(StatementType.CREDIT, bean.getType());
	}

	private TransactionBean verifyProviderTxsExpectations(List<TransactionBean> result) {
		assertEquals(1, result.size());
		TransactionBean bean = result.get(0);
		assertEquals(ApplicationState.PROVIDER_TX_PREFIX + providerTxId, bean.getReference());
		assertEquals(timestamp, bean.getTimestamp());
		assertEquals(amount, bean.getAmount());
		assertEquals(projectName, bean.getProjectName());
		return bean;
	}

	private void setProviderTxsExpectations() {
		when(providerTxRepository.findByUserAndDate(userId, startDate, endDate)).thenReturn(providerTxs);
		when(transactionHelper.createTransactionBean()).thenReturn(transactionBean);
		when(providerTx.getId()).thenReturn(providerTxId);
		when(providerTx.getTimestamp()).thenReturn(timestamp);
		when(providerTx.getAmount()).thenReturn(amount);
		when(providerTx.getProjectId()).thenReturn(projectId);
		when(projectRepository.findOne(projectId)).thenReturn(project);
		when(project.getName()).thenReturn(projectName);
	}
	
	@Test
	public void shouldNotGetProviderTransactions() throws Exception {
		when(providerTxRepository.findByUserAndDate(userId, startDate, endDate)).thenReturn(null);
		List<TransactionBean> result = transactionService.getProviderTransactions(userId, startDate, endDate);
		assertTrue(result.isEmpty());
	}
	
	@Test
	public void shouldGetProducerPartnershipProjectTransactions() throws Exception {
		when(projectTx.getType()).thenReturn(ProjectTxType.PRODUCER_PARTNERSHIP);

		setProjectTransactionsExpectations();
		
		List<TransactionBean> result = transactionService.getProjectTransactions(projectId);
		
		verifyProjectTransactionsExpectations(result);
		
		verify(bean).setDescription(ProjectTxType.PRODUCER_PARTNERSHIP.toString());
		verify(bean).setType(StatementType.CREDIT);
	}
	
	@Test
	public void shouldGetProducerPaymentProjectTransactions() throws Exception {
		when(projectTx.getType()).thenReturn(ProjectTxType.PRODUCER_PAYMENT);

		setProjectTransactionsExpectations();
		
		List<TransactionBean> result = transactionService.getProjectTransactions(projectId);
		
		verifyProjectTransactionsExpectations(result);
		
		verify(bean).setDescription(ProjectTxType.PRODUCER_PAYMENT.toString());
		verify(bean).setType(StatementType.DEBIT);
	}
	
	@Test
	public void shouldGetProducerFundingProjectTransactions() throws Exception {
		when(projectTx.getType()).thenReturn(ProjectTxType.PRODUCER_FUNDING);

		setProjectTransactionsExpectations();
		
		List<TransactionBean> result = transactionService.getProjectTransactions(projectId);
		
		verifyProjectTransactionsExpectations(result);
		
		verify(bean).setDescription(ProjectTxType.PRODUCER_FUNDING.toString());
		verify(bean).setType(StatementType.CREDIT);
	}
	
	@Test
	public void shouldGetReturnFundsProjectTransactions() throws Exception {
		when(projectTx.getType()).thenReturn(ProjectTxType.RETURN_FUNDS);

		setProjectTransactionsExpectations();
		
		List<TransactionBean> result = transactionService.getProjectTransactions(projectId);
		
		verifyProjectTransactionsExpectations(result);
		
		verify(bean).setDescription(ProjectTxType.RETURN_FUNDS.toString());
		verify(bean).setType(StatementType.DEBIT);
	}
	
	@Test
	public void shouldGetReturnTramaFeeProjectTransactions() throws Exception {
		when(projectTx.getType()).thenReturn(ProjectTxType.RETURN_TRAMA_FEE);

		setProjectTransactionsExpectations();
		
		List<TransactionBean> result = transactionService.getProjectTransactions(projectId);
		
		verifyProjectTransactionsExpectations(result);
		
		verify(bean).setDescription(ProjectTxType.RETURN_TRAMA_FEE.toString());
		verify(bean).setType(StatementType.CREDIT);
	}
	
	@Test
	public void shouldGetRedeemOutProjectTransactions() throws Exception {
		when(projectTx.getType()).thenReturn(ProjectTxType.REDEEM_OUT);

		setProjectTransactionsExpectations();
		
		List<TransactionBean> result = transactionService.getProjectTransactions(projectId);
		
		verifyProjectTransactionsExpectations(result);
		
		verify(bean).setDescription(ProjectTxType.REDEEM_OUT.toString());
		verify(bean).setType(StatementType.DEBIT);
	}
	
	@Test
	public void shouldGetRedeemInProjectTransactions() throws Exception {
		when(projectTx.getType()).thenReturn(ProjectTxType.REDEEM_IN);

		setProjectTransactionsExpectations();
		
		List<TransactionBean> result = transactionService.getProjectTransactions(projectId);
		
		verifyProjectTransactionsExpectations(result);
		
		verify(bean).setDescription(ProjectTxType.REDEEM_IN.toString());
		verify(bean).setType(StatementType.CREDIT);
	}
	
	@Test
	public void shouldGetFundingProjectTransactions() throws Exception {
		when(projectTx.getType()).thenReturn(ProjectTxType.FUNDING);

		setProjectTransactionsExpectations();
		
		List<TransactionBean> result = transactionService.getProjectTransactions(projectId);
		
		verifyProjectTransactionsExpectations(result);
		
		verify(bean).setDescription(ProjectTxType.FUNDING.toString());
		verify(bean).setType(StatementType.CREDIT);
	}
	
	@Test
	public void shouldGetInvestmentProjectTransactions() throws Exception {
		when(projectTx.getType()).thenReturn(ProjectTxType.INVESTMENT);

		setProjectTransactionsExpectations();
		
		List<TransactionBean> result = transactionService.getProjectTransactions(projectId);
		
		verifyProjectTransactionsExpectations(result);
		
		verify(bean).setDescription(ProjectTxType.INVESTMENT.toString());
		verify(bean).setType(StatementType.CREDIT);
	}
	
	@Test
	public void shouldGetTransferFromTramaTransactions() throws Exception {
		when(projectTx.getType()).thenReturn(ProjectTxType.TRANSFER_FROM_TRAMA);

		setProjectTransactionsExpectations();
		
		List<TransactionBean> result = transactionService.getProjectTransactions(projectId);
		
		verifyProjectTransactionsExpectations(result);
		
		verify(bean).setDescription(ProjectTxType.TRANSFER_FROM_TRAMA.toString());
		verify(bean).setType(StatementType.CREDIT);
	}
	
	@Test
	public void shouldGetProviderPaymentTransactions() throws Exception {
		when(projectTx.getType()).thenReturn(ProjectTxType.PROVIDER_PAYMENT);

		setProjectTransactionsExpectations();
		
		List<TransactionBean> result = transactionService.getProjectTransactions(projectId);
		
		verifyProjectTransactionsExpectations(result);
		
		verify(bean).setDescription(ProjectTxType.PROVIDER_PAYMENT.toString());
		verify(bean).setType(StatementType.DEBIT);
	}
	
	@Test
	public void shouldGetProviderPartnershipTransactions() throws Exception {
		when(projectTx.getType()).thenReturn(ProjectTxType.PROVIDER_PARTNERSHIP);

		setProjectTransactionsExpectations();
		
		List<TransactionBean> result = transactionService.getProjectTransactions(projectId);
		
		verifyProjectTransactionsExpectations(result);
		
		verify(bean).setDescription(ProjectTxType.PROVIDER_PARTNERSHIP.toString());
		verify(bean).setType(StatementType.CREDIT);
	}
	
	@Test
	public void shouldGetProviderReturnOfCapitalTransactions() throws Exception {
		when(projectTx.getType()).thenReturn(ProjectTxType.PROVIDER_RETURN_CAPITAL);

		setProjectTransactionsExpectations();
		
		List<TransactionBean> result = transactionService.getProjectTransactions(projectId);
		
		verifyProjectTransactionsExpectations(result);
		
		verify(bean).setDescription(ProjectTxType.PROVIDER_RETURN_CAPITAL.toString());
		verify(bean).setType(StatementType.DEBIT);
	}
	
	@Test
	public void shouldGetTramaFeeTransactions() throws Exception {
		when(projectTx.getType()).thenReturn(ProjectTxType.TRAMA_FEE);

		setProjectTransactionsExpectations();
		
		List<TransactionBean> result = transactionService.getProjectTransactions(projectId);
		
		verifyProjectTransactionsExpectations(result);
		
		verify(bean).setDescription(ProjectTxType.TRAMA_FEE.toString());
		verify(bean).setType(StatementType.DEBIT);
	}
	
	@Test
	public void shouldGetBoxOfficeSalesFromTramaTransactions() throws Exception {
		when(projectTx.getType()).thenReturn(ProjectTxType.BOX_OFFICE_SALES);

		setProjectTransactionsExpectations();
		
		List<TransactionBean> result = transactionService.getProjectTransactions(projectId);
		
		verifyProjectTransactionsExpectations(result);
		
		verify(bean).setDescription(ProjectTxType.BOX_OFFICE_SALES.toString());
		verify(bean).setType(StatementType.CREDIT);
	}
	
	@Test
	public void shouldGetRdiTransactions() throws Exception {
		when(projectTx.getType()).thenReturn(ProjectTxType.RDI);

		setProjectTransactionsExpectations();
		
		List<TransactionBean> result = transactionService.getProjectTransactions(projectId);
		
		verifyProjectTransactionsExpectations(result);
		
		verify(bean).setDescription(ProjectTxType.RDI.toString());
		verify(bean).setType(StatementType.DEBIT);
	}
	
	@Test
	public void shouldGetRdfTransactions() throws Exception {
		when(projectTx.getType()).thenReturn(ProjectTxType.RDF);

		setProjectTransactionsExpectations();
		
		List<TransactionBean> result = transactionService.getProjectTransactions(projectId);
		
		verifyProjectTransactionsExpectations(result);
		
		verify(bean).setDescription(ProjectTxType.RDF.toString());
		verify(bean).setType(StatementType.DEBIT);
	}

	private void verifyProjectTransactionsExpectations(List<TransactionBean> result) {
		verify(bean).setReference(ApplicationState.PROJECT_TX_PREFIX + projectId);
		verify(bean).setTimestamp(timestamp);
		verify(bean).setAmount(amount);
		
		assertEquals(1, result.size());
		assertEquals(bean, result.get(0));
	}

	private void setProjectTransactionsExpectations() {
		projectTxs.add(projectTx);
		when(projectTxRepository.getByProjectId(projectId)).thenReturn(projectTxs);
		when(transactionHelper.createTransactionBean()).thenReturn(bean);
		when(projectTx.getId()).thenReturn(projectId);
		when(projectTx.getTimestamp()).thenReturn(timestamp);
		when(projectTx.getAmount()).thenReturn(amount);
	}
	
	@Test
	public void shouldSaveReturnOfCapitalInjection() throws Exception {
		when(projectFinancialDataRepository.findOne(projectId)).thenReturn(projectFinancialData);
		when(userRepository.findOne(providerId)).thenReturn(provider);
		when(projectFinancialData.getBalance()).thenReturn(projectBalance);
		when(provider.getBalance()).thenReturn(providerBalance);
		
		transactionService.createReturnOfCapitalInjection(projectId, providerId, amount);
		
		verify(transactionApplier).substractAmount(projectFinancialData, amount);
		verify(transactionApplier).addAmount(provider, amount);
		
		verify(transactionLogService).createProjectLog(projectId, providerId, amount, ProjectTxType.PROVIDER_RETURN_CAPITAL);
		verify(transactionLogService).createUserLog(projectId, providerId, null, amount, null, TransactionType.PROVIDER_RETURN_CAPITAL);
	}
	
	@Test (expected=NonSufficientFundsException.class)
	public void shouldNotSaveReturnOfCapitalInjectionDueToNonSufficientFunds() throws Exception {
		when(projectFinancialDataRepository.findOne(projectId)).thenReturn(projectFinancialData);
		when(userRepository.findOne(providerId)).thenReturn(provider);
		when(projectFinancialData.getBalance()).thenReturn(zero);
		when(provider.getBalance()).thenReturn(providerBalance);
		
		transactionService.createReturnOfCapitalInjection(projectId, providerId, amount);
	}

	@Test (expected=ProjectNotExistException.class)
  public void shouldNotSaveReturnOfCapitalInjectionDueToNoProject() throws Exception {
    transactionService.createReturnOfCapitalInjection(projectId, providerId, amount);
  }
	
	@Test (expected=UserNotFoundException.class)
  public void shouldNotSaveReturnOfCapitalInjectionDueToNoUser() throws Exception {
	  when(projectFinancialDataRepository.findOne(projectId)).thenReturn(projectFinancialData);
    transactionService.createReturnOfCapitalInjection(projectId, providerId, amount);
  }
}
