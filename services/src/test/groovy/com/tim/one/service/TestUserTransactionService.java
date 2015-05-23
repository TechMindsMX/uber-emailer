package com.tim.one.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

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
import com.tim.one.collaborator.CalculatorCollaborator;
import com.tim.one.collaborator.UnitCollaborator;
import com.tim.one.exception.NotTramaAccountFoundException;
import com.tim.one.exception.UserNotFoundException;
import com.tim.one.helper.TransactionHelper;
import com.tim.one.model.Project;
import com.tim.one.model.ProjectFinancialData;
import com.tim.one.model.TramaAccount;
import com.tim.one.model.TramaTx;
import com.tim.one.model.UnitTx;
import com.tim.one.model.User;
import com.tim.one.model.UserTx;
import com.tim.one.packer.MessagePacker;
import com.tim.one.repository.ProjectRepository;
import com.tim.one.repository.TramaAccountRepository;
import com.tim.one.repository.UserRepository;
import com.tim.one.service.impl.UnitServiceImpl;
import com.tim.one.service.impl.UserTransactionServiceImpl;
import com.tim.one.state.ApplicationState;
import com.tim.one.util.DateUtil;

public class TestUserTransactionService {

	@InjectMocks
	private UserTransactionServiceImpl userTransactionService = new UserTransactionServiceImpl();
	
	@Mock
	private UnitCollaborator unitCollaborator;
	@Mock
	private UnitServiceImpl unitService;
	@Mock
	private List<UnitTx> units;
	@Mock
	private ProjectTransactionService projectTransactionService;
	@Mock
	private TramaAccount tramaAccount;
	@Mock
	private CalculatorCollaborator calculatorCollaborator;
	@Mock
	private TransactionHelper transactionHelper;
	@Mock
	private TramaTx tramaTx;
	@Mock
	private DateUtil dateUtil;
	@Mock
	private UserTx userTx;
	@Mock
	private UserRepository userRepository;
	@Mock
	private User user;
	@Mock
	private MessagePacker messagePacker;
	@Mock
	private Project project;
	@Mock
	private TransactionLogService transactionLogService;
	@Mock
	private Properties properties;
	@Mock
	private ProjectRepository projectRepository;
	@Mock
	private TramaAccountRepository tramaAccountRepository;
	@Mock
	private ProjectFinancialData projectFinancialData;
	@Mock
	private TransactionApplier transactionApplier;
	
	private Integer userId = 1;
	private Integer projectId = 2;
	private Integer transactionId = 3;
	
	private String tramaTransactionFee = "10";
	private String projectName = "projectName";
	private String email = "email";

	private Long timestamp = 1L;

	private BigDecimal tri = new BigDecimal("0.10");
	private BigDecimal trf = new BigDecimal("0.15");
	private BigDecimal amount = new BigDecimal("250");
	private BigDecimal mac = new BigDecimal("1000");
	private BigDecimal fee = new BigDecimal("100");
	private BigDecimal tramaAccountBalance = new BigDecimal("100000");
	private BigDecimal userBalance = new BigDecimal("5000");
	private BigDecimal deposit = new BigDecimal("150");


	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldGetRDI() throws Exception {
		BigDecimal expectedResult = new BigDecimal("25.00");
		
		when(projectFinancialData.getId()).thenReturn(projectId);
		when(unitCollaborator.getUnitsAmount(projectFinancialData, units)).thenReturn(amount);
		when(unitService.getUnitsByUserAndType(userId, TransactionType.INVESTMENT)).thenReturn(units);
		when(projectTransactionService.getTRI(projectFinancialData)).thenReturn(tri);
		
		assertEquals(expectedResult, userTransactionService.getRDI(userId, projectFinancialData));
	}
	
	@Test
	public void shouldGetRDF() throws Exception {
		BigDecimal expectedResult = new BigDecimal("37.50");
		
		when(projectFinancialData.getId()).thenReturn(projectId);
		when(unitCollaborator.getUnitsAmount(projectFinancialData, units)).thenReturn(amount);
		when(unitService.getUnitsByUserAndType(userId, TransactionType.FUNDING)).thenReturn(units);
		when(projectTransactionService.getTRF(projectFinancialData)).thenReturn(trf);
		
		assertEquals(expectedResult, userTransactionService.getRDF(userId, projectFinancialData));
	}
	
	@Test
	public void shouldGetRac() throws Exception {
		BigDecimal expectedResult = new BigDecimal("150.00");
		
		when(projectFinancialData.getId()).thenReturn(projectId);
		when(projectTransactionService.getTRF(projectFinancialData)).thenReturn(trf);
		when(projectTransactionService.getMAC(projectId)).thenReturn(mac);
		
		assertEquals(expectedResult, userTransactionService.getRAC(projectId, projectFinancialData));
	}
	
	@Test (expected=NotTramaAccountFoundException.class)
	public void shouldNotReturnOfFunding() throws Exception {
		userTransactionService.measureReturnOfInvestment(userId, projectFinancialData, amount, TransactionType.RDF);
	}
	
	@Test (expected=UserNotFoundException.class)
  public void shouldNotReturnOfFundingDueToNoUser() throws Exception {
	  when(tramaAccountRepository.findByType(AdminAccountType.TRAMA)).thenReturn(tramaAccount);
    userTransactionService.measureReturnOfInvestment(userId, projectFinancialData, amount, TransactionType.RDF);
  }
	
	@Test
	public void shouldReturnOfFunding() throws Exception {
		setRoiExpectations();
		
		userTransactionService.measureReturnOfInvestment(userId, projectFinancialData, amount, TransactionType.RDF);
		
		verify(transactionLogService).createTramaLog(userId, fee, EntityType.USER, TramaAccountType.INVESTMENT);
		verify(transactionApplier).addAmount(tramaAccount, fee);
		verify(transactionLogService).createUserLog(projectId, userId, null, deposit, null, TransactionType.RDF);
		verify(transactionApplier).addAmount(user, deposit);
		verify(transactionLogService).createProjectLog(projectId, userId, fee, ProjectTxType.TRAMA_FEE);
		verify(transactionLogService).createProjectLog(projectId, userId, deposit, ProjectTxType.RDF);
		messagePacker.sendAbonoCuenta(amount, projectName, email, transactionId, MessageType.CIERRE_PRODUCTO);
	}

	private void setRoiExpectations() {
		when(properties.getProperty(ApplicationState.TRAMA_TRANSACTION_FEE)).thenReturn(tramaTransactionFee);
		
		when(tramaAccountRepository.findByType(AdminAccountType.TRAMA)).thenReturn(tramaAccount);
		when(calculatorCollaborator.computePercentage(amount, new BigDecimal(tramaTransactionFee))).thenReturn(fee);
		when(transactionHelper.createTramaTx()).thenReturn(tramaTx);
		when(dateUtil.createDateAsLong()).thenReturn(timestamp);
		
		when(tramaAccount.getBalance()).thenReturn(tramaAccountBalance);
		
		when(transactionHelper.createUserTx()).thenReturn(userTx);
		when(userRepository.findOne(userId)).thenReturn(user);
		when(user.getBalance()).thenReturn(userBalance);
		when(user.getEmail()).thenReturn(email);

		when(projectFinancialData.getProject()).thenReturn(project);
		when(project.getId()).thenReturn(projectId);
		when(project.getName()).thenReturn(projectName);
	}
	
	@Test
	public void shouldReturnOfInvestment() throws Exception {
		setRoiExpectations();
		
		userTransactionService.measureReturnOfInvestment(userId, projectFinancialData, amount, TransactionType.RDI);
		
		verify(transactionApplier).addAmount(tramaAccount, fee);
		transactionLogService.createTramaLog(userId, fee, EntityType.USER, TramaAccountType.INVESTMENT);
		
		verify(transactionLogService).createUserLog(projectId, userId, null, deposit, null, TransactionType.RDI);
		verify(transactionApplier).addAmount(user, deposit);
		verify(transactionLogService).createProjectLog(projectId, userId, fee, ProjectTxType.TRAMA_FEE);
		verify(transactionLogService).createProjectLog(projectId, userId, deposit, ProjectTxType.RDI);
		messagePacker.sendAbonoCuenta(amount, projectName, email, transactionId, MessageType.CIERRE_PRODUCTO);
	}

}
