package com.tim.one.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.bean.AccountType;
import com.tim.one.bean.AdminAccountType;
import com.tim.one.bean.EntityType;
import com.tim.one.bean.ProjectTxType;
import com.tim.one.bean.TramaAccountType;
import com.tim.one.bean.TransactionType;
import com.tim.one.collaborator.AccountCollaborator;
import com.tim.one.collaborator.AccountIdentifier;
import com.tim.one.exception.NoAccountExistException;
import com.tim.one.exception.NonSufficientFundsException;
import com.tim.one.helper.TransactionHelper;
import com.tim.one.model.Project;
import com.tim.one.model.ProjectFinancialData;
import com.tim.one.model.ProjectTx;
import com.tim.one.model.TramaAccount;
import com.tim.one.model.TramaTx;
import com.tim.one.model.User;
import com.tim.one.model.UserAccount;
import com.tim.one.model.UserTx;
import com.tim.one.packer.MessagePacker;
import com.tim.one.repository.ProjectFinancialDataRepository;
import com.tim.one.repository.ProjectRepository;
import com.tim.one.repository.TramaAccountRepository;
import com.tim.one.repository.UserRepository;
import com.tim.one.service.impl.AccountServiceImpl;
import com.tim.one.stp.validator.ClabeValidator;
import com.tim.one.util.DateUtil;

public class TestAccountService {

	@InjectMocks
	private AccountServiceImpl accountService = new AccountServiceImpl();
	
	@Mock
	private AccountIdentifier accountIdentifier;
	@Mock
	private UserRepository userRepository;
	@Mock
	private User user;
	@Mock
	private ProjectFinancialData projectFinancialData;
	@Mock
	private Project project;
	@Mock
	private TramaAccount tramaAccount;
	@Mock
	private TransactionHelper transactionHelper;
	@Mock
	private DateUtil dateUtil;
	@Mock
	private UserTx userTx;
	@Mock
	private TramaTx tramaTx;
	@Mock
	private ProjectTx projectTx;
	@Mock
	private UserAccount userAccount;
	@Mock
	private MessagePacker messagePacker;
	@Mock
	private AccountCollaborator accountCollaborator;
	@Mock
	private TransactionLogService transactionLogService;
	@Mock
	private ClabeValidator clabeValidator;
	@Mock
	private ProjectFinancialDataRepository projectFinancialDataRepository;
	@Mock
	private ProjectRepository projectRepository;
	@Mock
	private TramaAccountRepository tramaAccountRepository;
	@Mock
	private TransactionApplier transactionApplier;
	
	private Integer projectId = 1;
	private Integer userId = 2;

	private String clabe = "account";
	private String userName = "userName";
	private String projectName = "projectName";

	private BigDecimal amount = new BigDecimal("1000");
	private BigDecimal tramaBalance = new BigDecimal("1300");

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldGetUserAccount() throws Exception {
		when(accountIdentifier.getTypeByAccount(clabe)).thenReturn(AccountType.USER);
		when(userRepository.findUserByAccount(clabe)).thenReturn(user);
		when(user.getName()).thenReturn(userName);
		
		assertEquals(userName, accountService.getAccount(clabe));
	}
	
	@Test
	public void shouldNotGetUserAccount() throws Exception {
		when(accountIdentifier.getTypeByAccount(clabe)).thenReturn(AccountType.USER);
		when(userRepository.findUserByAccount(clabe)).thenReturn(null);
		
		assertEquals(StringUtils.EMPTY, accountService.getAccount(clabe));
	}
	
	@Test
	public void shouldGetProjectAccount() throws Exception {
		when(accountIdentifier.getTypeByAccount(clabe)).thenReturn(AccountType.PROJECT);
		when(projectFinancialDataRepository.findProjectFinancialDataByAccount(clabe)).thenReturn(projectFinancialData);
		when(projectFinancialData.getId()).thenReturn(projectId);
		when(projectRepository.findOne(projectId)).thenReturn(project);
		when(project.getName()).thenReturn(projectName);
		
		assertEquals(projectName, accountService.getAccount(clabe));
	}
	
	@Test
	public void shouldNotGetProjectAccount() throws Exception {
		when(accountIdentifier.getTypeByAccount(clabe)).thenReturn(AccountType.PROJECT);
		assertEquals(StringUtils.EMPTY, accountService.getAccount(clabe));
	}
	
	@Test (expected=NonSufficientFundsException.class)
	public void shouldNotTransferFundsFromTramaDueToNonSufficientFunds() throws Exception {
		BigDecimal lowerAmount = new BigDecimal("500");
		when(tramaAccountRepository.findByType(AdminAccountType.TRAMA)).thenReturn(tramaAccount);
		when(tramaAccount.getBalance()).thenReturn(lowerAmount);
		
		accountService.transferFundsFromTramaTo(clabe, amount);
	}
	
	@Test (expected=NoAccountExistException.class)
	public void shouldNotTransferFundsFromTramaDueToNoValidAccount() throws Exception {
		when(tramaAccountRepository.findByType(AdminAccountType.TRAMA)).thenReturn(tramaAccount);
		when(accountIdentifier.getTypeByAccount(clabe)).thenReturn(AccountType.USER);
		when(tramaAccount.getBalance()).thenReturn(amount);
		when(userRepository.findUserByAccount(clabe)).thenReturn(null);
		
		accountService.transferFundsFromTramaTo(clabe, amount);
	}
	
	@Test 
	public void shouldTransferFundsFromTramaToUser() throws Exception {
		when(tramaAccountRepository.findByType(AdminAccountType.TRAMA)).thenReturn(tramaAccount);
		when(accountIdentifier.getTypeByAccount(clabe)).thenReturn(AccountType.USER);
		when(tramaAccount.getBalance()).thenReturn(tramaBalance);
		when(userRepository.findUserByAccount(clabe)).thenReturn(user);
		when(user.getId()).thenReturn(userId);
		
		accountService.transferFundsFromTramaTo(clabe, amount);
		
		verify(transactionApplier).addAmount(user, amount);
		verify(transactionLogService).createUserLog(null, userId, null, amount, null, TransactionType.TRANSFER_FROM_TRAMA);
		
		verify(transactionLogService).createTramaLog(userId, amount, EntityType.USER, TramaAccountType.TRANSFER_TO_PARTNER);
		verify(transactionApplier).substractAmount(tramaAccount, amount);
	}
	
	@Test 
	public void shouldTransferFundsFromTramaToProject() throws Exception {
		when(tramaAccountRepository.findByType(AdminAccountType.TRAMA)).thenReturn(tramaAccount);
		when(accountIdentifier.getTypeByAccount(clabe)).thenReturn(AccountType.PROJECT);
		when(tramaAccount.getBalance()).thenReturn(tramaBalance);
		when(projectFinancialDataRepository.findProjectFinancialDataByAccount(clabe)).thenReturn(projectFinancialData);
		
		when(transactionHelper.createProjectTx()).thenReturn(projectTx);
		when(projectFinancialData.getId()).thenReturn(projectId);
		
		accountService.transferFundsFromTramaTo(clabe, amount);
		
		verify(transactionApplier).addAmount(projectFinancialData, amount);
		verify(transactionApplier).substractAmount(tramaAccount, amount);
		verify(transactionLogService).createTramaLog(projectId, amount, EntityType.PROJECT, TramaAccountType.TRANSFER_TO_PARTNER);
		transactionLogService.createProjectLog(projectId, null, amount, ProjectTxType.TRANSFER_FROM_TRAMA);
	}
	
	@Test (expected=NoAccountExistException.class)
	public void shouldNotTransferFundsFromTramaToProjectDueToNoValidAccount() throws Exception {
		when(tramaAccountRepository.findByType(AdminAccountType.TRAMA)).thenReturn(tramaAccount);
		when(accountIdentifier.getTypeByAccount(clabe)).thenReturn(AccountType.PROJECT);
		when(tramaAccount.getBalance()).thenReturn(tramaBalance);

		accountService.transferFundsFromTramaTo(clabe, amount);
	}
	
}
