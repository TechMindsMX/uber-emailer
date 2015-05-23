package com.tim.one.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.bean.PaymentType;
import com.tim.one.bean.PurchaseTransactionBean;
import com.tim.one.bean.TransactionBean;
import com.tim.one.bean.TransferUserLimitBean;
import com.tim.one.exception.NonSufficientFundsException;
import com.tim.one.exception.UserNotFoundException;
import com.tim.one.service.SecurityService;
import com.tim.one.util.DateUtil;
import com.tim.one.exception.AdvanceProviderPaidException;
import com.tim.one.exception.NonProjectProviderException;
import com.tim.one.exception.NotBulkTransactionFoundExeption;
import com.tim.one.exception.NotValidParameterException;
import com.tim.one.exception.SettlementProviderPaidException;
import com.tim.one.exception.TransferUserLimitNotExistException;
import com.tim.one.helper.TransactionManagerHelper;
import com.tim.one.manager.TransactionFreakfundManager;
import com.tim.one.model.UnitTx;
import com.tim.one.model.UserTx;
import com.tim.one.service.TransactionService;
import com.tim.one.state.ErrorState;
import com.tim.one.state.ResponseState;

import com.tim.one.command.TransactionCommand;
import com.tim.one.command.ProviderPartnershipCommand;
import com.tim.one.command.TransferFundsCommand;
import com.tim.one.command.ContributeProducerCommand;
import com.tim.one.command.ProducerFundingCommand;
import com.tim.one.command.MaxAmountCommand;
import com.tim.one.command.DeleteLimitAmountCommand;
import com.tim.one.command.TransactionUserStatementCommand;
import com.tim.one.command.CapitalReturnCommand;

import com.tim.one.status.TimoneErrorStatus;

public class TestTransactionController {

  @InjectMocks
  private TransactionController transactionController = new TransactionController();

  @Mock
  private BigDecimal amount;
  @Mock
  private TransactionService transactionService;
  @Mock
  private SecurityService securityService;
  @Mock
  private HttpServletResponse servlerResponse;
  @Mock
  private List<TransferUserLimitBean> transferUserLimits;
  @Mock
  private UserTx userTx;
  @Mock
  private List<PurchaseTransactionBean> purchaseTransactionBeans;
  @Mock
  private TransactionBean transactionBean;
  @Mock
  private TransactionBean otherTransactionBean;
  @Mock
  private TransactionBean providerTransactionBean;
  @Mock
  private DateUtil dateUtil;
  @Mock
  private TransactionManagerHelper transactionManagerHelper;
  @Mock
  private List<TransactionBean> transactionBeans;
  @Mock
  private TransactionFreakfundManager transactionManager;
  @Mock
  private List<TransactionBean> beansWithBalance;
  @Mock
  private HttpServletResponse response;

  private Integer type = 1;
  private Integer senderId = 2;
  private Integer receiverId = 3;
  private Integer transactionId = 4;
  private Integer projectId = 5;
  private Integer providerId = 6;
  private Integer userId = 7;
  private Integer destinationId = 8;
  private Integer bulkId = 9;

  private String token = "token";
  private String callback = "callback";
  private String start = "start";
  private String end = "end";

  private Long startDate = 1L;
  private Long endDate = 2L;

  private BigDecimal balance = new BigDecimal("1000");
  private BigDecimal zero = new BigDecimal("0");

  private List<TransactionBean> unitTransactionBeans = new ArrayList<TransactionBean>();

  private TransactionCommand transactionCommand;
  private ProviderPartnershipCommand transactionSaveProviderCommand;
  private TransferFundsCommand transactionTransferFundsCommand;
  private ContributeProducerCommand transactionContributeProducerCommand;
  private ProducerFundingCommand transactionProducerFundingCommand;
  private MaxAmountCommand transactionMaxAmountCommand;
  private DeleteLimitAmountCommand transactionDeleteLimitAmountCommand;
  private TransactionUserStatementCommand transactionUserStatementCommand;
  private CapitalReturnCommand transactionCapitalReturnCommand;

  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
    transactionTransferFundsCommand = new TransferFundsCommand();
    transactionTransferFundsCommand.setSenderId(senderId);
    transactionTransferFundsCommand.setReceiverId(receiverId);
    transactionTransferFundsCommand.setAmount(amount);
    transactionTransferFundsCommand.setToken(token);
    transactionTransferFundsCommand.setCallback(callback);

    transactionCommand = new TransactionCommand();
    transactionCommand.setTransactionId(transactionId);
    transactionCommand.setUserId(userId);
    transactionCommand.setProjectId(projectId);
    transactionCommand.setBulkId(bulkId);

    transactionSaveProviderCommand = new ProviderPartnershipCommand();
    transactionSaveProviderCommand.setProviderId(providerId);
    transactionSaveProviderCommand.setProjectId(projectId);
    transactionSaveProviderCommand.setType(type);
    transactionSaveProviderCommand.setToken(token);
    transactionSaveProviderCommand.setCallback(callback);

    transactionContributeProducerCommand = new ContributeProducerCommand();
    transactionContributeProducerCommand.setProjectId(projectId);
    transactionContributeProducerCommand.setToken(token);
    transactionContributeProducerCommand.setCallback(callback);

    transactionProducerFundingCommand = new ProducerFundingCommand();
    transactionProducerFundingCommand.setProjectId(projectId);
    transactionProducerFundingCommand.setAmount(amount);
    transactionProducerFundingCommand.setToken(token);
    transactionProducerFundingCommand.setCallback(callback);

    transactionMaxAmountCommand = new MaxAmountCommand();
    transactionMaxAmountCommand.setUserId(userId);
    transactionMaxAmountCommand.setDestinationId(destinationId);
    transactionMaxAmountCommand.setAmount(amount);
    transactionMaxAmountCommand.setToken(token);

    transactionDeleteLimitAmountCommand = new DeleteLimitAmountCommand();
    transactionDeleteLimitAmountCommand.setUserId(userId);
    transactionDeleteLimitAmountCommand.setDestinationId(destinationId);
    transactionDeleteLimitAmountCommand.setToken(token);

    transactionUserStatementCommand = new TransactionUserStatementCommand();
    transactionUserStatementCommand.setUserId(userId);
    transactionUserStatementCommand.setStartDate(start);
    transactionUserStatementCommand.setEndDate(end);

    transactionCapitalReturnCommand = new CapitalReturnCommand();
    transactionCapitalReturnCommand.setProjectId(projectId);
    transactionCapitalReturnCommand.setProviderId(providerId);
    transactionCapitalReturnCommand.setAmount(amount);
    transactionCapitalReturnCommand.setToken(token);
    transactionCapitalReturnCommand.setCallback(callback);
  }

  @Test
  public void shouldNotTransferFundsDueToNoValidToken() throws Exception {
    when(securityService.isValid(token)).thenReturn(false);
    transactionController.transferFunds(transactionTransferFundsCommand, response);
    verify(response).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.TOKEN_EXPIRED));
  }

  @Test
  public void shouldTransferFunds() throws Exception {
    when(securityService.isValid(token)).thenReturn(true);
    when(transactionService.transferFunds(senderId, receiverId, amount)).thenReturn(transactionId);

    transactionController.transferFunds(transactionTransferFundsCommand, response);

    verify(transactionService).transferFunds(senderId, receiverId, amount);
    verify(response).sendRedirect(callback + ResponseState.getResponse(transactionId));
    verify(response, never()).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.TOKEN_EXPIRED));
  }

  @Test
  public void shouldNotTransferFundsDueToUserNotFound() throws Exception {
    when(securityService.isValid(token)).thenReturn(true);
    when(transactionService.transferFunds(senderId, receiverId, amount)).thenThrow(new UserNotFoundException(senderId));

    transactionController.transferFunds(transactionTransferFundsCommand, response);
    verify(response).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.USER_NOT_FOUND));
  }

  @Test
  public void shouldNotTransferFundsDueToUserNonSufficientFounds() throws Exception {
    when(securityService.isValid(token)).thenReturn(true);
    when(transactionService.transferFunds(senderId, receiverId, amount)).thenThrow(new NonSufficientFundsException(senderId));

    transactionController.transferFunds(transactionTransferFundsCommand, response);
    verify(response).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.NON_SUFFICIENT_FUNDS));
  }

  @Test
  public void shouldNotTransferFundsDueToInvalidParameter() throws Exception {
    when(securityService.isValid(token)).thenReturn(true);
    when(transactionService.transferFunds(senderId, receiverId, amount)).thenThrow(new NotValidParameterException(senderId));

    transactionController.transferFunds(transactionTransferFundsCommand, response);
    verify(response).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.INCORRECT_PARAMETER_REQUEST));
  }

  @Test
  public void shouldGetTransaction() throws Exception {
    when(transactionService.getTransactions(transactionId)).thenReturn(purchaseTransactionBeans);
    assertEquals(purchaseTransactionBeans, transactionController.getTransaction(transactionCommand));
  }

  @Test
  public void shouldNotGetTransaction() throws Exception {
    when(transactionService.getTransactions(transactionId)).thenThrow(new NotBulkTransactionFoundExeption(transactionId));
    assertEquals(new ArrayList<UnitTx>(), transactionController.getTransaction(transactionCommand));
  }

  @Test
  public void shouldSaveProviderPartnership() throws Exception {
    when(securityService.isValid(token)).thenReturn(true);

    transactionController.saveProviderPartnership(transactionSaveProviderCommand, response);

    verify(transactionService).createProviderPartnership(providerId, projectId, PaymentType.SETTLEMENT);
    verify(response).sendRedirect(callback);
    verify(response, never()).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.TOKEN_EXPIRED));
  }

  public void shouldNotSaveProviderPartnershipDueToNoValidToken() throws Exception {
    when(securityService.isValid(token)).thenReturn(false);

    transactionController.saveProviderPartnership(transactionSaveProviderCommand, response);
    verify(response).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.TOKEN_EXPIRED));
  }

  public void shouldNotSaveProviderPartnershipDueToNoProviderException() throws Exception {
    when(securityService.isValid(token)).thenReturn(true);
    when(transactionService.createProviderPartnership(providerId, projectId, PaymentType.getTypeByCode(type))).thenThrow(new NonProjectProviderException(providerId));

    transactionController.saveProviderPartnership(transactionSaveProviderCommand, response);
    verify(response).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.PROVIDER_NOT_FOUND));
  }

  public void shouldNotSaveProviderPartnershipDueToAdvanceProviderException() throws Exception {
    when(securityService.isValid(token)).thenReturn(true);
    when(transactionService.createProviderPartnership(providerId, projectId, PaymentType.getTypeByCode(type))).thenThrow(new AdvanceProviderPaidException(providerId));

    transactionController.saveProviderPartnership(transactionSaveProviderCommand, response);
    verify(response).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.ADVANCE_PAYMENT_ALREADY_PAID));
  }

  public void shouldNotSaveProviderPartnershipDueToSettlementProviderPaidException() throws Exception {
    when(securityService.isValid(token)).thenReturn(true);
    when(transactionService.createProviderPartnership(providerId, projectId, PaymentType.getTypeByCode(type))).thenThrow(new SettlementProviderPaidException(providerId));

    transactionController.saveProviderPartnership(transactionSaveProviderCommand, response);
    verify(response).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.SETTLEMENT_ALREADY_PAID));
  }

  @Test
  public void shouldContributeProducerRevenue() throws Exception {
    when(securityService.isValid(token)).thenReturn(true);

    transactionController.contributeProducerRevenue(transactionContributeProducerCommand, response);

    verify(transactionService).createProducerPartnership(projectId);
    verify(response).sendRedirect(callback);
    verify(response, never()).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.TOKEN_EXPIRED));
  }

  @Test
  public void shouldNotContributeProducerRevenueDueToNoValidToken() throws Exception {
    when(securityService.isValid(token)).thenReturn(false);

    transactionController.contributeProducerRevenue(transactionContributeProducerCommand, response);

    verify(transactionService, never()).createProducerPartnership(projectId);
    verify(response).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.TOKEN_EXPIRED));
  }

  @Test
  public void shouldNotContributeProducerRevenueDueToUserNotFound() throws Exception {
    when(securityService.isValid(token)).thenReturn(true);
    when(transactionService.createProducerPartnership(projectId)).thenThrow(new UserNotFoundException(projectId));

    transactionController.contributeProducerRevenue(transactionContributeProducerCommand, response);
    verify(response).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.USER_NOT_FOUND));
  }

  @Test
  public void shouldProducerFunding() throws Exception {
    when(securityService.isValid(token)).thenReturn(true);

    transactionController.producerFunding(transactionProducerFundingCommand, response);

    verify(transactionService).createProducerFunding(projectId, amount);
    verify(response).sendRedirect(callback);
    verify(response, never()).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.TOKEN_EXPIRED));
  }

  @Test
  public void shouldNotProducerFundingDueToNoValidToken() throws Exception {
    when(securityService.isValid(token)).thenReturn(false);

    transactionController.producerFunding(transactionProducerFundingCommand, response);

    verify(response).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.TOKEN_EXPIRED));
    verify(transactionService, never()).createProducerFunding(projectId, amount);
  }

  @Test
  public void shouldNotProducerFundingDueToUserNotFoundException() throws Exception {
    when(securityService.isValid(token)).thenReturn(true);
    when(transactionService.createProducerFunding(projectId, amount)).thenThrow(new UserNotFoundException(projectId));

    transactionController.producerFunding(transactionProducerFundingCommand, response);
    verify(response).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.USER_NOT_FOUND));
  }

  @Test
  public void shouldNotProducerFundingDueToNonSufficientFundsException() throws Exception {
    when(securityService.isValid(token)).thenReturn(true);
    when(transactionService.createProducerFunding(projectId, amount)).thenThrow(new NonSufficientFundsException(projectId));

    transactionController.producerFunding(transactionProducerFundingCommand, response);
    verify(response).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.NON_SUFFICIENT_FUNDS));
  }

  @Test
  public void shouldSetMaxAmountToTransfer() throws Exception {
    when(securityService.isValid(token)).thenReturn(true);
    when(transactionService.measureAmountToTransfer(userId, destinationId, amount)).thenReturn(transactionId);

    assertEquals(ResponseState.getJsonResponse(transactionId),transactionController.maxAmountToTransfer(transactionMaxAmountCommand, servlerResponse));

    verify(servlerResponse).addHeader("Allow-Control-Allow-Methods", "POST");
    verify(servlerResponse).addHeader("Access-Control-Allow-Origin", "*");
  }

  @Test
  public void shouldNotSetMaxAmountToTransferDueToNoValidToken() throws Exception {
    when(securityService.isValid(token)).thenReturn(false);

    assertEquals(ErrorState.getJsonErrorCode(TimoneErrorStatus.TOKEN_EXPIRED),transactionController.maxAmountToTransfer(transactionMaxAmountCommand, servlerResponse));
  }

  @Test
  public void shouldNotSetMaxAmountToTransferDueToNoUserFound() throws Exception {
    when(securityService.isValid(token)).thenReturn(true);
    when(transactionService.measureAmountToTransfer(userId, destinationId, amount)).thenThrow(new UserNotFoundException(userId));

    assertEquals(ErrorState.getJsonErrorCode(TimoneErrorStatus.USER_NOT_FOUND),transactionController.maxAmountToTransfer(transactionMaxAmountCommand, servlerResponse));
  }

  @Test
  public void shouldNotSetMaxAmountToTransferDueToNoValidParameter() throws Exception {
    when(securityService.isValid(token)).thenReturn(true);
    when(transactionService.measureAmountToTransfer(userId, destinationId, amount)).thenThrow(new NotValidParameterException(destinationId));

    assertEquals(ErrorState.getJsonErrorCode(TimoneErrorStatus.INCORRECT_PARAMETER_REQUEST),transactionController.maxAmountToTransfer(transactionMaxAmountCommand, servlerResponse));
  }

  @Test
  public void shouldGetLimitAmountsToTransfer() throws Exception {
    when(transactionService.getLimitAmountsToTransfer(userId)).thenReturn(transferUserLimits);
    assertEquals(transferUserLimits, transactionController.getLimitAmountsToTransfer(transactionCommand));
  }

  @Test
  public void shouldDeleteLimitAmountToTransfer() throws Exception {
    when(securityService.isValid(token)).thenReturn(true);

    assertEquals(ResponseState.getJsonResponse(200), transactionController.deleteLimitAmountToTransfer(transactionDeleteLimitAmountCommand, servlerResponse));

    verify(transactionService).changeLimitAmountToTransfer(userId, destinationId);
    verify(servlerResponse).addHeader("Allow-Control-Allow-Methods", "POST");
    verify(servlerResponse).addHeader("Access-Control-Allow-Origin", "*");
  }

  @Test
  public void shouldNotDeleteLimitAmountToTransferDueToToken() throws Exception {
    when(securityService.isValid(token)).thenReturn(false);

    assertEquals(ErrorState.getJsonErrorCode(TimoneErrorStatus.TOKEN_EXPIRED), transactionController.deleteLimitAmountToTransfer(transactionDeleteLimitAmountCommand, servlerResponse));

    verify(transactionService, never()).changeLimitAmountToTransfer(userId, destinationId);
  }

  @Test
  public void shouldNotDeleteLimitAmountToTransferDueToNoTransferUserLimitExist() throws Exception {
    when(securityService.isValid(token)).thenReturn(true);
    when(transactionService.changeLimitAmountToTransfer(userId, destinationId)).thenThrow(new TransferUserLimitNotExistException(userId, destinationId));

    assertEquals(ErrorState.getJsonErrorCode(TimoneErrorStatus.NOT_LIMIT_AMOUNT_REGISTER), transactionController.deleteLimitAmountToTransfer(transactionDeleteLimitAmountCommand, servlerResponse));
  }

  @Test
  public void shouldGetUserTransaction() throws Exception {
    when(transactionService.getUserTransaction(transactionId)).thenReturn(userTx);
    assertEquals(userTx, transactionController.getUserTransaction(transactionCommand));
  }

  @Test
  public void shouldGetUserStatement() throws Exception {
    when(dateUtil.dateStartFormat(start)).thenReturn(startDate);
    when(dateUtil.dateEndFormat(end)).thenReturn(endDate);
    when(transactionManagerHelper.getUserTransactionBeans(userId, startDate, endDate)).thenReturn(transactionBeans);
    when(transactionManager.getStartingBalance(userId, start)).thenReturn(balance);
    when(transactionService.setBalances(transactionBeans, balance)).thenReturn(beansWithBalance);

    List<TransactionBean> result = transactionController.getUserStatement(transactionUserStatementCommand);

    assertEquals(beansWithBalance, result);
  }

  @Test
  public void shouldNotGetUserStatementDueToParseException() throws Exception {
    when(dateUtil.dateStartFormat(start)).thenThrow(new ParseException(start, 0));
    List<TransactionBean> result = transactionController.getUserStatement(transactionUserStatementCommand);
    assertTrue(result.isEmpty());
  }

  @Test
  public void shouldGetBulkTransactions() throws Exception {
    when(transactionService.getBulkTransactions(bulkId)).thenReturn(unitTransactionBeans);
    assertEquals(unitTransactionBeans, transactionController.getBulkTransactions(transactionCommand));
  }

  @Test
  public void shouldGetProjectStatement() throws Exception {
    when(transactionManagerHelper.getProjectTransactionBeans(projectId)).thenReturn(transactionBeans);
    when(transactionService.setBalances(transactionBeans, zero)).thenReturn(beansWithBalance);

    assertEquals(beansWithBalance,transactionController.getProjectStatement(transactionCommand));
  }

  @Test
  public void shouldReturnOfCapitalInjection() throws Exception {
    when(securityService.isValid(token)).thenReturn(true);
    transactionController.returnOfCapitalInjection(transactionCapitalReturnCommand, response);

    verify(transactionService).createReturnOfCapitalInjection(projectId, providerId, amount);
    verify(response).sendRedirect(callback);
    verify(response, never()).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.TOKEN_EXPIRED));
  }

  @Test
  public void shouldNotReturnOfCapitalInjectionDueToNoValidCode() throws Exception {
    when(securityService.isValid(token)).thenReturn(false);
    transactionController.returnOfCapitalInjection(transactionCapitalReturnCommand, response);
    verify(transactionService, never()).createReturnOfCapitalInjection(projectId, providerId, amount);
    verify(response).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.TOKEN_EXPIRED));
  }

  @Test
  public void shouldNotReturnOfCapitalInjectionDueToNonSufficientFunds() throws Exception {
    when(securityService.isValid(token)).thenReturn(true);
    when(transactionService.createReturnOfCapitalInjection(projectId, providerId, amount)).thenThrow(new NonSufficientFundsException(projectId));

    transactionController.returnOfCapitalInjection(transactionCapitalReturnCommand, response);
    verify(response).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.NON_SUFFICIENT_FUNDS));
  }

}
