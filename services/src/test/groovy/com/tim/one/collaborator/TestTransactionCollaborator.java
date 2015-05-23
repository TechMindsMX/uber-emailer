package com.tim.one.collaborator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.bean.AdminAccountType;
import com.tim.one.bean.EntityType;
import com.tim.one.bean.MessageType;
import com.tim.one.bean.ProjectTxType;
import com.tim.one.bean.TramaAccountType;
import com.tim.one.bean.TransactionType;
import com.tim.one.collaborator.MessageCollaborator;
import com.tim.one.collaborator.RevertCollaborator;
import com.tim.one.collaborator.TransactionCollaborator;
import com.tim.one.collaborator.UserCollaborator;
import com.tim.one.exception.NonSufficientFundsException;
import com.tim.one.exception.NotTramaAccountFoundException;
import com.tim.one.helper.TransactionHelper;
import com.tim.one.model.BulkUnitTx;
import com.tim.one.model.Project;
import com.tim.one.model.ProjectFinancialData;
import com.tim.one.model.ProjectTx;
import com.tim.one.model.ProjectUnitSale;
import com.tim.one.model.TramaAccount;
import com.tim.one.model.TramaTx;
import com.tim.one.model.TransferUserLimit;
import com.tim.one.model.UnitTx;
import com.tim.one.model.User;
import com.tim.one.model.UserTx;
import com.tim.one.packer.MessagePacker;
import com.tim.one.repository.ProjectFinancialDataRepository;
import com.tim.one.repository.ProjectRepository;
import com.tim.one.repository.ProjectUnitSaleRepository;
import com.tim.one.repository.TramaAccountRepository;
import com.tim.one.repository.TramaTxRepository;
import com.tim.one.repository.TransferUserLimitRepository;
import com.tim.one.repository.UserRepository;
import com.tim.one.repository.UserTxRepository;
import com.tim.one.service.TransactionApplier;
import com.tim.one.service.TransactionLogService;
import com.tim.one.service.impl.CalculatorServiceImpl;
import com.tim.one.service.impl.UserTransactionServiceImpl;
import com.tim.one.state.ApplicationState;
import com.tim.one.util.DateUtil;

public class TestTransactionCollaborator {

	@InjectMocks
	private TransactionCollaborator transactionCollaborator = new TransactionCollaborator();
	
	@Mock
	private UserTx userTx;
	@Mock
	private TramaAccount tramaAccount;
	@Mock
	private ProjectFinancialData projectFinancialData;
	@Mock
	private CalculatorServiceImpl calculatorService;
	@Mock
	private TransactionHelper transactionHelper;
	@Mock
	private TramaTx tramaTxCredit;
	@Mock
	private TramaTx tramaTxDebit;
	@Mock
	private DateUtil dateUtil;
	@Mock
	private RevertCollaborator revertCollaborator;
	@Mock
	private Project project;
	@Mock
	private TransferUserLimit transferUserLimit;
	@Mock
	private Set<User> users;
	@Mock
	private MessagePacker messagePacker;
	@Mock
	private ProjectTx projectTx;
	@Mock
	private UserCollaborator userCollaborator;
	@Mock
	private UserTransactionServiceImpl userTransactionService;
	@Mock
	private UnitTx unitTx;
	@Mock
	private ProjectUnitSale projectUnitSale;
	@Mock
	private UserRepository userRepository;
	@Mock
	private User productor;
	@Mock
	private MessageCollaborator messageCollaborator;
	@Mock
	private BulkUnitTx bulk;
	@Mock
	private TransactionLogService transactionLogService;
	@Mock
	private Properties properties;
	@Mock
	private ProjectFinancialDataRepository projectFinancialDataRepository;
  @Mock
  private ProjectRepository projectRepository;
  @Mock
  private ProjectUnitSaleRepository projectUnitSaleRepository;
  @Mock
  private UserTxRepository userTxRepository;
  @Mock
  private TransferUserLimitRepository transferUserLimitRepository;
  @Mock
  private TramaAccountRepository tramaAccountRepository;
  @Mock
  private TramaTxRepository tramaTxRepository;
  @Mock
  private TransactionApplier transactionApplier;

	private Integer projectId = 1;
	private Integer reason = 2;
	private Integer destinationId = 3;
	private Integer userId = 7;
	private Integer financierId = 8;
	private Integer investorId = 9;
	private Integer pendienteStatusAsInteger=10;
	private Integer projectUnitSaleId = 11;
	private Integer quantity = 12;
	private Integer produccionStatusAsInteger = 6;
	
	private BigDecimal fee = new BigDecimal("5000");
	private BigDecimal projectBalance = new BigDecimal("25000");
	private BigDecimal breakeven = new BigDecimal("20000");
	private BigDecimal amount = new BigDecimal("30000");
	private BigDecimal fundingAmount = new BigDecimal("40000");
	private BigDecimal investmentAmount = new BigDecimal("50000");
	private BigDecimal budget = new BigDecimal("20000");
	private BigDecimal unitSale = new BigDecimal("100");
	private BigDecimal tramaBalance = new BigDecimal("60000");
	private BigDecimal zero = new BigDecimal("0");

	private String producerRevenuePercentage = "20";
	private String pendienteStatus = "10";
	private String projectName = "projectName";
	private String email = "email";
	private String produccionStatus = "6";
	private String noBreakevenReachedReason = "2";
	private String fundingEndDays = "90";

	private Set<Integer> financierIds = new HashSet<Integer>();
	private Set<Integer> investorIds = new HashSet<Integer>();
	private Set<UnitTx> units = new HashSet<UnitTx>();

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldLog() throws Exception {
		transactionCollaborator.log(userTx);
		verify(userTxRepository).save(userTx);
	}
	
	@Test
	public void shouldSetTramaCharge() throws Exception {
		when(tramaAccountRepository.findByType(AdminAccountType.TRAMA)).thenReturn(tramaAccount);
		when(projectFinancialDataRepository.findOne(projectId)).thenReturn(projectFinancialData);
		when(tramaAccount.getBalance()).thenReturn(tramaBalance);
		when(projectFinancialData.getBalance()).thenReturn(projectBalance);
		when(projectFinancialData.getTramaFee()).thenReturn(fee);
		when(project.getProjectFinancialData()).thenReturn(projectFinancialData);
		when(project.getId()).thenReturn(projectId);
		
		transactionCollaborator.setTramaCharge(project);
		
		verify(transactionApplier).addAmount(tramaAccount, fee);
		verify(transactionApplier).substractAmount(projectFinancialData, fee);
		
		verify(transactionLogService).createTramaLog(projectId, fee, EntityType.PROJECT, TramaAccountType.PRODUCT);
		verify(transactionLogService).createProjectLog(projectId, null, fee, ProjectTxType.TRAMA_FEE);
	}
	
	@Test (expected=NotTramaAccountFoundException.class)
	public void shouldNotSetTramaChargeDueToNoTramaAccount() throws Exception {
		transactionCollaborator.setTramaCharge(project);
	}
	
	@Test (expected=NonSufficientFundsException.class)
	public void shouldNotSetTramaChargeDueToNotSufficientFunds() throws Exception {
		when(tramaAccountRepository.findByType(AdminAccountType.TRAMA)).thenReturn(tramaAccount);
		when(projectFinancialDataRepository.findOne(projectId)).thenReturn(projectFinancialData);
		when(project.getProjectFinancialData()).thenReturn(projectFinancialData);
		when(projectFinancialData.getBalance()).thenReturn(zero);
		when(projectFinancialData.getTramaFee()).thenReturn(fee);
		
		transactionCollaborator.setTramaCharge(project);
	}
	
	@Test
	public void shouldRevertTramaChargeAndSendNoBreakevenNotification() throws Exception {
		BigDecimal expectedBalance = setRevertTramaChargeExpectations();
		when(project.getProjectFinancialData()).thenReturn(projectFinancialData);
		when(project.getId()).thenReturn(projectId);
		
		transactionCollaborator.revertTramaCharge(project, reason);
		
		verifyRevertTramaChargeExpectations(expectedBalance);

		verify(transactionLogService).createTramaLog(projectId, fee, EntityType.PROJECT, TramaAccountType.PROJECT_REJECTED);
		verify(transactionLogService).createProjectLog(projectId, null, fee, ProjectTxType.RETURN_TRAMA_FEE);
		
		verify(messagePacker).sendCierreProductor(project);
		verify(messagePacker).sendCierreAdministrador(project);
	}
	
	@Test
	public void shouldRevertTramaChargeAndNotSendNoBreakevenNotification() throws Exception {
		BigDecimal expectedBalance = setRevertTramaChargeExpectations();
		when(project.getProjectFinancialData()).thenReturn(projectFinancialData);
		when(project.getId()).thenReturn(projectId);
		
		when(transactionHelper.createTramaTx()).thenReturn(tramaTxDebit);
		
		transactionCollaborator.revertTramaCharge(project, 100);
		
		verifyRevertTramaChargeExpectations(expectedBalance);

		verify(messagePacker, never()).sendCierreProductor(project);
	}
	
	@Test (expected=NonSufficientFundsException.class)
	public void shouldNotRevertTramaChargeDueToNonSufficientFunds() throws Exception {
		when(projectRepository.findOne(projectId)).thenReturn(project);
		when(project.getStatus()).thenReturn(produccionStatusAsInteger);
		when(tramaTxRepository.findByEntityIdAndType(projectId, TramaAccountType.PRODUCT)).thenReturn(tramaTxCredit);
		when(tramaAccountRepository.findByType(AdminAccountType.TRAMA)).thenReturn(tramaAccount);
		when(project.getProjectFinancialData()).thenReturn(projectFinancialData);
		when(project.getId()).thenReturn(projectId);
		when(tramaAccount.getBalance()).thenReturn(zero);
		when(tramaTxCredit.getAmount()).thenReturn(fee);
		when(properties.getProperty(ApplicationState.PRODUCTION_STATUS)).thenReturn(produccionStatus);
		
		transactionCollaborator.revertTramaCharge(project, 100);
	}

	private void verifyRevertTramaChargeExpectations(BigDecimal expectedBalance) {
	  verify(transactionApplier).substractAmount(tramaAccount, fee);
	  verify(transactionApplier).addAmount(projectFinancialData, fee);
	}

	private BigDecimal setRevertTramaChargeExpectations() throws NonSufficientFundsException {
		when(projectRepository.findOne(projectId)).thenReturn(project);
		when(project.getStatus()).thenReturn(produccionStatusAsInteger);
		when(properties.getProperty(ApplicationState.PRODUCTION_STATUS)).thenReturn(produccionStatus);
		when(properties.getProperty(ApplicationState.NO_BREAKEVEN_REACHED_REASON)).thenReturn(noBreakevenReachedReason);
		
		BigDecimal expectedBalance = new BigDecimal("30000");
		
		when(tramaAccountRepository.findByType(AdminAccountType.TRAMA)).thenReturn(tramaAccount);
		when(tramaTxRepository.findByEntityIdAndType(projectId, TramaAccountType.PRODUCT)).thenReturn(tramaTxCredit);
		when(tramaAccount.getBalance()).thenReturn(tramaBalance);
		when(tramaTxCredit.getAmount()).thenReturn(fee);
		
		when(projectFinancialDataRepository.findOne(projectId)).thenReturn(projectFinancialData);
		when(projectFinancialData.getBalance()).thenReturn(projectBalance);
		when(revertCollaborator.revertTransactions(project, reason)).thenReturn(users);
		return expectedBalance;
	}
	
	@Test
	public void shouldAddFunds() throws Exception {
		BigDecimal expectedResult = new BigDecimal("55000");
		when(projectFinancialDataRepository.findOne(projectId)).thenReturn(projectFinancialData);
		when(projectFinancialData.getBalance()).thenReturn(projectBalance);
		
		transactionCollaborator.addFunds(projectId, amount);
		
		verify(projectFinancialData).setBalance(expectedResult);
		verify(projectFinancialDataRepository).save(projectFinancialData);
	}
	
	@Test
	public void shouldGetProducerRevenue() throws Exception {
		BigDecimal expectedResult = new BigDecimal("4000");
		when(properties.getProperty(ApplicationState.PRODUCER_REVENUE_PERCENTAGE)).thenReturn(producerRevenuePercentage);
		
		assertEquals(expectedResult, transactionCollaborator.getProducerRevenue(budget));
	}
	
	@Test
	public void shouldSavePendienteStatus() throws Exception {
		when(properties.getProperty(ApplicationState.PENDIENTE_STATUS)).thenReturn(pendienteStatus);
		
		when(project.getId()).thenReturn(projectId);
		when(projectFinancialData.getBalance()).thenReturn(projectBalance);
		when(projectFinancialData.getBreakeven()).thenReturn(breakeven);
		
		transactionCollaborator.verifyBreakeven(project, projectFinancialData);
		
		verify(project).setStatus(pendienteStatusAsInteger);
		verify(projectRepository).save(project);
		verify(messagePacker).sendBreakevenReachedProductor(project);
		verify(messagePacker).sendBreakevenReachedAdministrador(project);
		verify(messageCollaborator).sendBreakevenReachedPartners(project);
	}
	
	@Test
	public void shouldSaveNotPendienteStatus() throws Exception {
		BigDecimal projectBalance = new BigDecimal("15000");
		setVerifyBreakevenExpectations(projectBalance);
		
		transactionCollaborator.verifyBreakeven(project, projectFinancialData);
		
		verify(project, never()).setStatus(pendienteStatusAsInteger);
		verify(projectRepository, never()).save(project);
	}

	private void setVerifyBreakevenExpectations(BigDecimal projectBalance) {
		when(properties.getProperty(ApplicationState.PENDIENTE_STATUS)).thenReturn(pendienteStatus);
		
		when(project.getId()).thenReturn(projectId);
		when(projectFinancialData.getBalance()).thenReturn(projectBalance);
		when(projectFinancialData.getBreakeven()).thenReturn(breakeven);
	}
	
	@Test
	public void shouldSaveAndPersist() throws Exception {
		transactionCollaborator.save(userTx);
		verify(userTxRepository).save(userTx);
	}
	
	@Test
	public void shouldGetExistingTransferUserLimit() throws Exception {
		when(transferUserLimitRepository.findByUserIdAndDestinationId(userId, destinationId)).thenReturn(transferUserLimit);
		assertEquals(transferUserLimit, transactionCollaborator.getTransferUserLimit(userId, destinationId));
	}
	
	@Test
	public void shouldGetNewTransferUserLimit() throws Exception {
		transactionCollaborator.getTransferUserLimit(userId, destinationId);
		verify(transactionHelper).createTransferUserLimit();
	}
	
	@Test
	public void shouldSetFundingEndDate() throws Exception {
		when(properties.getProperty(ApplicationState.FUNDING_END_DAYS)).thenReturn(fundingEndDays);
		when(dateUtil.createDate()).thenReturn(new Date());
		when(projectFinancialDataRepository.findOne(projectId)).thenReturn(projectFinancialData);
		
		Calendar c = Calendar.getInstance();
		c.setTime(dateUtil.createDate());
		c.add(Calendar.DATE, new Integer(fundingEndDays));
		
		transactionCollaborator.setFundingEndDate(projectFinancialData);
		
		verify(projectFinancialData).setFundEndDate(c.getTime().getTime());
		verify(projectFinancialDataRepository).save(projectFinancialData);
	}
	
	@Test
	public void shouldSaveTransferUserLimit() throws Exception {
		transactionCollaborator.save(transferUserLimit);
		verify(transferUserLimitRepository).save(transferUserLimit);
	}
	
	@Test
	public void shouldCloseProject() throws Exception {
		financierIds.add(financierId);
		investorIds.add(investorId);
		
		when(project.getProjectFinancialData()).thenReturn(projectFinancialData);
		when(project.getId()).thenReturn(projectId);
		
		when(projectFinancialDataRepository.findOne(projectId)).thenReturn(projectFinancialData);
		when(userCollaborator.getPartnersByType(projectFinancialData, TransactionType.FUNDING)).thenReturn(financierIds);
		when(userCollaborator.getPartnersByType(projectFinancialData, TransactionType.INVESTMENT)).thenReturn(investorIds);
		when(userTransactionService.getRDF(financierId, projectFinancialData)).thenReturn(fundingAmount);
		when(userTransactionService.getRDI(investorId, projectFinancialData)).thenReturn(investmentAmount);
		
		when(projectRepository.findOne(projectId)).thenReturn(project);
		when(project.getUser()).thenReturn(productor);
		when(project.getName()).thenReturn(projectName);
		when(projectFinancialData.getBalance()).thenReturn(projectBalance);
		when(productor.getEmail()).thenReturn(email);
		
		transactionCollaborator.closeProject(project);
		
		verify(userTransactionService).measureReturnOfInvestment(financierId, projectFinancialData, fundingAmount, TransactionType.RDF);
		verify(userTransactionService).measureReturnOfInvestment(investorId, projectFinancialData, investmentAmount, TransactionType.RDI);
		verify(messagePacker).sendAbonoCuenta(projectBalance, projectName, email, 0, MessageType.CIERRE_PRODUCTO_PRODUCTOR);
		verify(messagePacker).sendCierreVentaProductor(project, projectFinancialData);
	}
	
	@Test
	public void shouldGetDescription() throws Exception {
		units.add(unitTx);
		when(unitTx.getType()).thenReturn(TransactionType.FUNDING);
		assertEquals(TransactionType.FUNDING, transactionCollaborator.getDescription(units));
	}
	
	@Test
	public void shouldGetAmount() throws Exception {
		BigDecimal expectedAmount = new BigDecimal("1200");

		units.add(unitTx);
		when(unitTx.getProjectUnitSaleId()).thenReturn(projectUnitSaleId);
		when(projectUnitSaleRepository.findOne(projectUnitSaleId)).thenReturn(projectUnitSale);
		when(unitTx.getQuantity()).thenReturn(quantity);
		when(projectUnitSale.getUnitSale()).thenReturn(unitSale);
		
		BigDecimal result = transactionCollaborator.getAmount(units);
		
		assertEquals(expectedAmount, result);
	}
	
	@Test
	public void shouldSetProjectName() throws Exception {
		when(bulk.getProjectName()).thenReturn(null);
		setProjectNameExpectations();
		
		transactionCollaborator.setProjectName(bulk, projectUnitSaleId);
		
		verify(bulk).setProjectName(projectName);
	}
	
	@Test
	public void shouldNotSetProjectName() throws Exception {
		when(bulk.getProjectName()).thenReturn(projectName);
		setProjectNameExpectations();
		
		transactionCollaborator.setProjectName(bulk, projectUnitSaleId);
		
		verify(bulk, never()).setProjectName(projectName);
	}

	private void setProjectNameExpectations() {
		when(projectUnitSaleRepository.findOne(projectUnitSaleId)).thenReturn(projectUnitSale);
		when(projectUnitSale.getProjectFinancialData()).thenReturn(projectFinancialData);
		when(projectFinancialData.getProject()).thenReturn(project);
		when(project.getId()).thenReturn(projectId);
		when(projectRepository.findOne(projectId)).thenReturn(project);
		when(project.getName()).thenReturn(projectName);
	}

}
