package com.tim.one.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.collaborator.AccountCollaborator;
import com.tim.one.collaborator.UserAccountCollaborator;
import com.tim.one.exception.UserNotFoundException;
import com.tim.one.model.User;
import com.tim.one.model.UserAccount;
import com.tim.one.packer.CommonMessagePacker;
import com.tim.one.repository.UserAccountRepository;
import com.tim.one.repository.UserRepository;
import com.tim.one.service.impl.STPAccountServiceImpl;
import com.tim.one.stp.exception.DuplicatedAccountException;
import com.tim.one.stp.exception.InvalidClabeException;
import com.tim.one.stp.service.STPClabeService;
import com.tim.one.stp.state.ApplicationState;
import com.tim.one.stp.validator.ClabeValidator;

public class TestSTPAccountService {
	
	@InjectMocks
	private STPAccountService accountService = new STPAccountServiceImpl();
	
	@Mock
	private AccountCollaborator accountCollaborator;
	@Mock
	private UserAccountCollaborator userAccountCollaborator;
	@Mock
	private UserAccount userAccount;
	@Mock
	private UserRepository userRepository;
	@Mock
	private User user;
	@Mock
	private ClabeValidator clabeValidator;
	@Mock
	private CommonMessagePacker messagePacker;
	@Mock
	private STPClabeService stpClabeService;
	@Mock
	private UserAccountRepository userAccountRepository;

	private Integer userId = 1;
	private Integer bankCode = 2;
	private Integer userAccountId = 3;

	private String email = "email";
	private String bankName = "bankName";
	private String clabe = "clabe";
  private String stpClabe = "stpClabe";

  @Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldCreateAccount() throws Exception {
		when(userAccountCollaborator.getUserAccount(user)).thenReturn(userAccount);
		when(userRepository.findOne(userId)).thenReturn(user);
		when(user.getEmail()).thenReturn(email);
		when(user.getId()).thenReturn(userId);
		when(accountCollaborator.getBankName(bankCode)).thenReturn(bankName);
		when(stpClabeService.generateSTPAccount(userId, ApplicationState.STP_FREAKFUND_PREFIX)).thenReturn(stpClabe);
		when(userAccount.getId()).thenReturn(userAccountId);
		when(userAccount.getStpClabe()).thenReturn(stpClabe);
		when(clabeValidator.isValid(clabe)).thenReturn(true);
		
		accountService.createAccout(userId, bankCode, clabe);
		
		verify(userAccount).setBankCode(bankCode);
		verify(userAccount).setClabe(clabe);
		verify(userAccount).setStpClabe(stpClabe);
		verify(userAccount).setUser(user);
		
		verify(userRepository).save(user);
		
		verify(messagePacker).sendCreacionCuenta(stpClabe, bankName, email);
	}
	
	@Test (expected=UserNotFoundException.class)
	public void shouldNotCreateAccountDueToUserNotFound() throws Exception {
		when(userRepository.findOne(userId)).thenReturn(null);
		accountService.createAccout(userId, bankCode, clabe);
	}
	
	@Test (expected=DuplicatedAccountException.class)
	public void shouldNotCreateAccountDueToDuplicatedAccountException() throws Exception {
		when(userRepository.findOne(userId)).thenReturn(user);
		when(userRepository.findUserByClabe(clabe)).thenReturn(user);
		accountService.createAccout(userId, bankCode, clabe);
	}
	
	@Test (expected=InvalidClabeException.class)
	public void shouldNotCreateAccountDueToInvalidClabeException() throws Exception {
		when(userRepository.findOne(userId)).thenReturn(user);
		when(userRepository.findUserByClabe(clabe)).thenReturn(null);
		when(clabeValidator.isValid(clabe)).thenReturn(false);
		
		accountService.createAccout(userId, bankCode, clabe);
	}
	
	@Test
	public void shouldGetAccountByUserId() throws Exception {
		when(userAccountRepository.findOne(userId)).thenReturn(userAccount);
		assertEquals(userAccount, accountService.getAccountByUserId(userId));
	}
	
}
