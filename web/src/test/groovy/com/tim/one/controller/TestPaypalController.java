package com.tim.one.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.collaborator.TransactionCollaborator;
import com.tim.one.command.PaypalPaymentCommand;
import com.tim.one.command.PaypalPaymentCompleteCommand;
import com.tim.one.model.User;
import com.tim.one.model.UserTx;
import com.tim.one.paypal.bean.PaypalBean;
import com.tim.one.paypal.helper.PaypalHelper;
import com.tim.one.paypal.service.PaypalService;
import com.tim.one.paypal.state.ApplicationState;
import com.tim.one.service.PaypalPaymentStorerService;
import com.tim.one.service.SecurityService;
import com.tim.one.service.TransactionLogService;
import com.tim.one.state.ApprovalStorer;
import com.tim.one.state.ErrorState;
import com.tim.one.status.TimoneErrorStatus;
import com.tim.one.util.DateUtil;

public class TestPaypalController {

	@InjectMocks
	private PaypalController paypalController = new PaypalController();

	@Mock
	private SecurityService securityService;
	@Mock
	private PaypalHelper paypalHelper;
	@Mock
	private PaypalService paypalService;
	@Mock
	private PaypalBean paypalBean;
	@Mock
	private User user;
	@Mock
	private UserTx userTx;
	@Mock
	private DateUtil dateUtil;
	@Mock
	private TransactionCollaborator transactionCollaborator;
	@Mock
	private TransactionLogService transactionLogService;
	@Mock
	private HttpServletResponse response;
	@Mock
	private PaypalPaymentStorerService paypalPaymentStorerService;
	@Mock
	private Properties properties;

  private PaypalPaymentCommand paypalPaymentCommand;
  private PaypalPaymentCompleteCommand paypalPaymentCompleteCommand;

	private String token = "token";
	private String callback = "callback";
	private String returnUrl = "returnUrl";
	private String guid = "guid";
	private String paymentId = "paymentId";
	private String approvalUrl = "approvalUrl";
	private String PayerID = "PayerID";

	private BigDecimal amount = new BigDecimal("1000");
	private BigDecimal balance = new BigDecimal("2500");

	private Integer userId = 1;
	private Long timestamp = 1L;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);

    paypalPaymentCommand = new PaypalPaymentCommand();
    paypalPaymentCommand.setUserId(userId);
    paypalPaymentCommand.setToken(token);
    paypalPaymentCommand.setCallback(callback);
    paypalPaymentCommand.setAmount(amount);

    paypalPaymentCompleteCommand = new PaypalPaymentCompleteCommand();
    paypalPaymentCompleteCommand.setGuid(guid);
    paypalPaymentCompleteCommand.setToken(token);
    paypalPaymentCompleteCommand.setPayerID(PayerID);
	}

	@Test
	public void shouldPayment() throws Exception {
		setPaypalExpectations();
		when(paypalBean.getPaymentId()).thenReturn(paymentId);
		when(properties.getProperty(ApplicationState.RETURN_URL)).thenReturn(returnUrl);

		paypalController.payment(paypalPaymentCommand, response);

		verify(paypalBean).setCallback(callback);
		assertEquals(paypalBean, ApprovalStorer.APPROVALS.get(guid));
		verify(response).sendRedirect(approvalUrl);
		verify(response, never()).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.TOKEN_EXPIRED));
	}

	private void setPaypalExpectations() {
		when(securityService.isValid(token)).thenReturn(true);
		when(paypalHelper.createGuid()).thenReturn(guid);
		when(paypalService.cashIn(userId, null, null, amount, null, callback, returnUrl + guid)).thenReturn(paypalBean);
		when(paypalBean.getApprovalUrl()).thenReturn(approvalUrl);
	}

	@Test
	public void shouldNotPayIfNoValidToken() throws Exception {
		when(securityService.isValid(token)).thenReturn(false);

		paypalController.payment(paypalPaymentCommand, response);

		verify(response).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.TOKEN_EXPIRED));
		verify(paypalService, never()).cashIn(userId, null, null, amount, null, callback, returnUrl + guid);
	}

	@Test
	public void shouldNotPayIfNoPaymentId() throws Exception {
		setPaypalExpectations();
		when(properties.getProperty(ApplicationState.RETURN_URL)).thenReturn(returnUrl);

		paypalController.payment(paypalPaymentCommand, response);

		verify(response).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.PAYPAL_ERROR));
		verify(paypalService).cashIn(userId, null, null, amount, null, callback, returnUrl + guid);
		verify(paypalBean, never()).setCallback(callback);
	}

	@Test
	public void shouldCompletePayment() throws Exception {
		ApprovalStorer.APPROVALS.put(guid, paypalBean);
		when(paypalBean.getCallback()).thenReturn(callback);
		when(paypalBean.getUserId()).thenReturn(userId);
		when(paypalPaymentStorerService.saveUserPayment(userId, amount)).thenReturn(balance);
		when(paypalBean.getAmount()).thenReturn(amount);
		when(paypalBean.getTimestamp()).thenReturn(timestamp);
		when(dateUtil.createDateAsLong()).thenReturn(timestamp);

		paypalController.completePayment(paypalPaymentCompleteCommand, response);

		verify(response).sendRedirect(callback + "&balance=" + balance + "&amount=" + amount + "&timestamp=" + timestamp);
	}

}
