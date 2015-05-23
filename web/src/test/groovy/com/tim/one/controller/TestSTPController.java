package com.tim.one.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tim.one.command.StpCashInCommand;
import com.tim.one.command.StpConfirmOrderCommand;
import com.tim.one.command.StpRegisterOrderCommand;
import com.tim.one.exception.AccountNotFoundException;
import com.tim.one.exception.BusinessException;
import com.tim.one.exception.InvalidAmountException;
import com.tim.one.exception.NonSufficientFundsException;
import com.tim.one.exception.UserNotFoundException;
import com.tim.one.model.BankCode;
import com.tim.one.service.STPConsumerService;
import com.tim.one.service.SecurityService;
import com.tim.one.state.ErrorState;
import com.tim.one.state.ResponseState;
import com.tim.one.status.TimoneErrorStatus;
import com.tim.one.stp.exception.InvalidClabeException;
import com.tim.one.stp.exception.SPEITransactionException;
import com.tim.one.stp.exception.STPResponseNotExists;
import com.tim.one.stp.exception.STPResponseNotValid;

public class TestSTPController {

	@InjectMocks
	private STPController stpController = new STPController();

	@Mock
	private HttpServletResponse servlerResponse;
	@Mock
	private SecurityService securityService;
	@Mock
	private STPConsumerService stpService;
	@Mock
	private List<BankCode> bankCodes;
	@Mock
	private HttpServletRequest request;

  private StpCashInCommand stpCashInCommand;
  private StpConfirmOrderCommand stpConfirmOrderCommand;
  private StpRegisterOrderCommand stpRegisterOrderCommand;

	private Integer estado = 0;
	private Integer userId = 1;
	private Integer speiId = 2;
	private Integer claveRastreo = 3;

	private Long timestamp = 1L;

	private String token = "token";
	private String callback = "callback";

	private BigDecimal amount = new BigDecimal("1000");
	private String clabeOrdenante = "clabeOrdenante";
	private String clabeBeneficiario = "clabeBeneficiario";
	private String reference = "reference";

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);

    stpRegisterOrderCommand = new StpRegisterOrderCommand();
    stpRegisterOrderCommand.setUserId(userId);
    stpRegisterOrderCommand.setAmount(amount);
    stpRegisterOrderCommand.setToken(token);
    stpRegisterOrderCommand.setCallback(callback);

    stpCashInCommand = new StpCashInCommand();
    stpCashInCommand.setAmount(amount);
    stpCashInCommand.setClabeOrdenante(clabeOrdenante);
    stpCashInCommand.setClabeBeneficiario(clabeBeneficiario);
    stpCashInCommand.setReference(reference);
    stpCashInCommand.setToken(token);

    stpConfirmOrderCommand = new StpConfirmOrderCommand();
    stpConfirmOrderCommand.setId(speiId);
    stpConfirmOrderCommand.setClaveRastreo(claveRastreo);
    stpConfirmOrderCommand.setEstado(estado);
    stpConfirmOrderCommand.setTimestamp(timestamp);
    stpConfirmOrderCommand.setToken(token);
	}

	@Test
	public void shouldNotRegistraOrdenDueToInvalidToken() throws Exception {
		when(securityService.isValid(token)).thenReturn(false);

		stpController.cashOut(stpRegisterOrderCommand, servlerResponse);

		verify(servlerResponse).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.TOKEN_EXPIRED));
	}

	@Test
	public void shouldNotRegistraOrdenDueToAccountNotFound() throws Exception {
		when(securityService.isValid(token)).thenReturn(true);
		when(stpService.cashOut(userId, amount)).thenThrow(new AccountNotFoundException(userId));

		stpController.cashOut(stpRegisterOrderCommand, servlerResponse);

		verify(servlerResponse).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.ACCOUNT_NOT_FOUND));
	}

	@Test
	public void shouldNotRegistraOrdenDueToNonSufficientFunds() throws Exception {
		when(securityService.isValid(token)).thenReturn(true);
		when(stpService.cashOut(userId, amount)).thenThrow(new NonSufficientFundsException(userId));

		stpController.cashOut(stpRegisterOrderCommand, servlerResponse);

		verify(servlerResponse).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.NON_SUFFICIENT_FUNDS));
	}

	@Test
	public void shouldNotRegistraOrdenDueToSpeiTransactionException() throws Exception {
		when(securityService.isValid(token)).thenReturn(true);
		when(stpService.cashOut(userId, amount)).thenThrow(new SPEITransactionException(clabeOrdenante));

		stpController.cashOut(stpRegisterOrderCommand, servlerResponse);

		verify(servlerResponse).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.STP_TRANSACTION_ERROR));
	}

	@Test
	public void shouldRegistraOrdenDueToSpeiTransactionException() throws Exception {
		when(securityService.isValid(token)).thenReturn(true);
		when(stpService.cashOut(userId, amount)).thenReturn(speiId);

		stpController.cashOut(stpRegisterOrderCommand, servlerResponse);

		verify(servlerResponse).sendRedirect(callback + ResponseState.getResponse(speiId));
		verify(servlerResponse, never()).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.TOKEN_EXPIRED));
	}

	@Test
	public void shouldListBankCodes() throws Exception {
		when(stpService.listBankCodes()).thenReturn(bankCodes);
		assertEquals(bankCodes, stpController.listBankCodes());
	}

	@Test
	public void shouldRegisterCashIn() throws Exception {
		when(securityService.isValid(token)).thenReturn(true);
		ResponseEntity<String> result = stpController.cashIn(stpCashInCommand, request);
		assertEquals(HttpStatus.OK, result.getStatusCode());
		verify(stpService).cashIn(null, clabeOrdenante, clabeBeneficiario, amount, reference, null, null);
	}

	@Test
	public void shouldNotRegisterCashInDueToTokenNoValid() throws Exception {
		when(securityService.isValid(token)).thenReturn(false);
		ResponseEntity<String> result = stpController.cashIn(stpCashInCommand, request);
		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
	}

	@Test
	public void shouldNotRegisterCashInDueToAccountNotFound() throws Exception {
		when(securityService.isValid(token)).thenReturn(true);
		when(stpService.cashIn(null, clabeOrdenante, clabeBeneficiario, amount, reference, null, null)).thenThrow(new BusinessException("be", new AccountNotFoundException(1)));
		ResponseEntity<String> result = stpController.cashIn(stpCashInCommand, request);
		assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
	}

	@Test
	public void shouldNotRegisterCashInDueToUserNotFound() throws Exception {
		when(securityService.isValid(token)).thenReturn(true);
		when(stpService.cashIn(null, clabeOrdenante, clabeBeneficiario, amount, reference, null, null)).thenThrow(new BusinessException("be", new UserNotFoundException(1)));
		ResponseEntity<String> result = stpController.cashIn(stpCashInCommand, request);
		assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
	}

	@Test
	public void shouldNotRegisterCashInDueToInvalidClabe() throws Exception {
		when(securityService.isValid(token)).thenReturn(true);
		when(stpService.cashIn(null, clabeOrdenante, clabeBeneficiario, amount, reference, null, null)).thenThrow(new BusinessException("be", new InvalidClabeException(clabeOrdenante)));
		ResponseEntity<String> result = stpController.cashIn(stpCashInCommand, request);
		assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
	}

	@Test
	public void shouldNotRegisterCashInDueToInvalidAmount() throws Exception {
		when(securityService.isValid(token)).thenReturn(true);
		when(stpService.cashIn(null, clabeOrdenante, clabeBeneficiario, amount, reference, null, null)).thenThrow(new BusinessException("be", new InvalidAmountException(amount)));
		System.out.println("1");
		ResponseEntity<String> result = stpController.cashIn(stpCashInCommand, request);
		System.out.println(result);
		assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
	}

	@Test
	public void shouldNotRegisterCashInDueToInvalidAmountNotNumeric() throws Exception {
		when(securityService.isValid(token)).thenReturn(true);
		when(stpService.cashIn(null, clabeOrdenante, clabeBeneficiario, amount, reference, null, null)).thenThrow(new BusinessException("be", new NumberFormatException()));
		ResponseEntity<String> result = stpController.cashIn(stpCashInCommand, request);
		assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
	}

	@Test
	public void shouldConfirmaOrden() throws Exception {
		when(securityService.isValid(token)).thenReturn(true);
		ResponseEntity<String> result = stpController.confirmaOrden(stpConfirmOrderCommand);
		assertEquals(HttpStatus.OK, result.getStatusCode());
		verify(stpService).confirmaOrden(speiId, claveRastreo, estado, timestamp);
	}

	@Test
	public void shouldNotConfirmaOrdenDueToSTPResponseNotExists() throws Exception {
		when(securityService.isValid(token)).thenReturn(true);
		when(stpService.confirmaOrden(speiId, claveRastreo, estado, timestamp)).thenThrow(new BusinessException("be", new STPResponseNotExists(speiId)));
		ResponseEntity<String> result = stpController.confirmaOrden(stpConfirmOrderCommand);
		assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
	}

	@Test
	public void shouldNotConfirmaOrdenDueToSTPResponseNotValid() throws Exception {
		when(securityService.isValid(token)).thenReturn(true);
		when(stpService.confirmaOrden(speiId, claveRastreo, estado, timestamp)).thenThrow(new BusinessException("be", new STPResponseNotValid(speiId)));
		ResponseEntity<String> result = stpController.confirmaOrden(stpConfirmOrderCommand);
		assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
	}

}
