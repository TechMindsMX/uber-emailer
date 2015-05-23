package com.tim.one.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.command.AccountCommand;
import com.tim.one.command.CreateAccountCommand;
import com.tim.one.command.TransferAccountCommand;
import com.tim.one.exception.InvalidAmountException;
import com.tim.one.exception.NoAccountExistException;
import com.tim.one.exception.NonSufficientFundsException;
import com.tim.one.exception.UserNotFoundException;
import com.tim.one.model.UserAccount;
import com.tim.one.service.AccountService;
import com.tim.one.service.impl.STPAccountServiceImpl;
import com.tim.one.service.impl.SecurityServiceImpl;
import com.tim.one.state.ErrorState;
import com.tim.one.status.TimoneErrorStatus;
import com.tim.one.stp.exception.DuplicatedAccountException;
import com.tim.one.stp.exception.InvalidClabeException;

public class TestAccountController {

	@InjectMocks
	private AccountController accountController = new AccountController();

	@Mock
	private AccountService accountService;
	@Mock
	private STPAccountServiceImpl stpAccountService;
	@Mock
	private SecurityServiceImpl securityService;
	@Mock
	private UserAccount userAccount;
	@Mock
	private HttpServletResponse response;

  private AccountCommand accountCommand;
  private TransferAccountCommand transferAccountCommand;
  private CreateAccountCommand createAccountCommand;

	private Integer tramaId = 1;
	private Integer userId = 2;

	private String account = "account";
	private String token = "token";
	private String callback = "callback";
	private String clabe = "clabe";

	private Integer bankCode = 40072;

	private BigDecimal amount = new BigDecimal("1000");

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
    accountCommand = new AccountCommand();
    accountCommand.setAccount(account);
    accountCommand.setUserId(userId);

    transferAccountCommand = new TransferAccountCommand();
    transferAccountCommand.setAmount(amount);
    transferAccountCommand.setAccount(account);
    transferAccountCommand.setToken(token);
    transferAccountCommand.setCallback(callback);

    createAccountCommand = new CreateAccountCommand();
    createAccountCommand.setUserId(userId);
    createAccountCommand.setBankCode(bankCode);
    createAccountCommand.setClabe(clabe);
    createAccountCommand.setToken(token);
    createAccountCommand.setCallback(callback);
	}

	@Test
	public void shouldGetAccount() throws Exception {
		accountController.getAccount(accountCommand);
		verify(accountService).getAccount(account);
	}

	@Test
	public void shouldTransferFundsFromTrama() throws Exception {
		when(securityService.isValid(token)).thenReturn(true);

		accountController.transferFundsFromTrama(transferAccountCommand, response);

		verify(accountService).transferFundsFromTramaTo(account, amount);
		verify(response).sendRedirect(callback);
		verify(response, never()).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.TOKEN_EXPIRED));
	}

	@Test
	public void shouldNotTransferFundsFromTramaDueToNotValidToken() throws Exception {
		when(securityService.isValid(token)).thenReturn(false);
		accountController.transferFundsFromTrama(transferAccountCommand, response);
		verify(accountService, never()).transferFundsFromTramaTo(account, amount);
		verify(response).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.TOKEN_EXPIRED));
	}

	@Test
	public void shouldNotTransferFundsFromTramaDueNonSufficientFunds() throws Exception {
		when(securityService.isValid(token)).thenReturn(true);
		when(accountService.transferFundsFromTramaTo(account, amount)).thenThrow(new NonSufficientFundsException(tramaId));
		accountController.transferFundsFromTrama(transferAccountCommand, response);
		verify(response).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.NON_SUFFICIENT_FUNDS));
	}

	@Test
  public void shouldNotTransferFundsFromTramaDueInvalidAmount() throws Exception {
    when(securityService.isValid(token)).thenReturn(true);
    when(accountService.transferFundsFromTramaTo(account, amount)).thenThrow(new InvalidAmountException(amount));
    accountController.transferFundsFromTrama(transferAccountCommand, response);
    verify(response).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.INVALID_AMOUNT));
  }
	
	@Test
	public void shouldNotTransferFundsFromTramaDueNoValidAccount() throws Exception {
		when(securityService.isValid(token)).thenReturn(true);
		when(accountService.transferFundsFromTramaTo(account, amount)).thenThrow(new NoAccountExistException(account));

		accountController.transferFundsFromTrama(transferAccountCommand, response);

		verify(response).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.ACCOUNT_NUMBER_NOT_VALID));
		verify(response, never()).sendRedirect(callback);
	}

	@Test
	public void shouldCreateAccount() throws Exception {
		when(securityService.isValid(token)).thenReturn(true);

		accountController.createAccount(createAccountCommand, response);

		verify(stpAccountService).createAccout(userId, bankCode, clabe);
		verify(response).sendRedirect(callback);
		verify(response, never()).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.TOKEN_EXPIRED));
	}

	@Test
	public void shouldNotCreateAccountDueToInvalidToken() throws Exception {
		when(securityService.isValid(token)).thenReturn(false);
		accountController.createAccount(createAccountCommand, response);
		verify(stpAccountService, never()).createAccout(userId, bankCode, clabe);
		verify(response).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.TOKEN_EXPIRED));

	}

	@Test
	public void shouldNotCreateAccountDueToUserNoExist() throws Exception {
		when(securityService.isValid(token)).thenReturn(true);
		when(stpAccountService.createAccout(userId, bankCode, clabe)).thenThrow(new UserNotFoundException(userId));
		accountController.createAccount(createAccountCommand, response);
		verify(response).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.USER_NOT_FOUND));
	}

	@Test
	public void shouldNotCreateAccountDueToDuplicatedAccount() throws Exception {
		when(securityService.isValid(token)).thenReturn(true);
		when(stpAccountService.createAccout(userId, bankCode, clabe)).thenThrow(new DuplicatedAccountException(clabe));

		accountController.createAccount(createAccountCommand, response);

		verify(response).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.CLABE_ACCOUNT_DUPLICATE));
		verify(response, never()).sendRedirect(callback);
	}

	@Test
	public void shouldNotCreateAccountDueToInvalidClabe() throws Exception {
		when(securityService.isValid(token)).thenReturn(true);
		when(stpAccountService.createAccout(userId, bankCode, clabe)).thenThrow(new InvalidClabeException(clabe));
		accountController.createAccount(createAccountCommand, response);
		verify(response).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.INVALID_CLABE_ACCOUNT));
	}

	@Test
	public void shouldGetAccountByUserId() throws Exception {
		when(stpAccountService.getAccountByUserId(userId)).thenReturn(userAccount);
		assertEquals(userAccount, accountController.getAccountByUserId(accountCommand));
	}

}
