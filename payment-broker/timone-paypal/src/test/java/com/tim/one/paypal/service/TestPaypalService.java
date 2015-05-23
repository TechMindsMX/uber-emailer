package com.tim.one.paypal.service;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.core.rest.PayPalRESTException;
import com.tim.one.paypal.bean.PaypalBean;
import com.tim.one.paypal.collabotator.PaypalCollaborator;
import com.tim.one.paypal.exception.ApprovalLinkException;
import com.tim.one.paypal.helper.PaypalHelper;
import com.tim.one.paypal.service.impl.PaypalServiceImpl;

public class TestPaypalService {

	@InjectMocks
	private PaypalService paypalService = new PaypalServiceImpl();
	
	@Mock
	private PaypalCollaborator paypalCollaborator;
	@Mock
	private Payment payment;
	@Mock
	private PaypalHelper paypalHelper;
	@Mock
	private RedirectUrls redirectUrls;
	@Mock
	private PaypalBean paypalBean;
	@Mock
	private OauthService oauthService;
	@Mock
	private Payment createdPayment;
	@Mock
	private List<Links> links;
	@Mock
	private PaymentExecution paymentExecution;
	
	private String cancelUrl = "cancelUrl";
	private String returnUrl = "returnUrl";
	private String token = "token";
	private String approvalUrl = "approvalUrl";
	private String paymentId = "paymentId";
	private String payerID = "payerID";
	private String paypalToken = "paypalToken";

	private BigDecimal amount = new BigDecimal("2500");

	private Integer userId = 1;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldStartPayment() throws Exception {
		setStartPaymentExpectations();
		when(payment.create(token)).thenReturn(createdPayment);
		when(createdPayment.getLinks()).thenReturn(links);
		when(paypalCollaborator.getApprovalLink(links)).thenReturn(approvalUrl);
		when(createdPayment.getId()).thenReturn(paymentId);
		
		paypalService.cashIn(userId, null, null, amount, null, cancelUrl, returnUrl);
		
		verify(redirectUrls).setCancelUrl(cancelUrl);
		verify(redirectUrls).setReturnUrl(returnUrl);
		verify(payment).setRedirectUrls(redirectUrls);
		
		verify(paypalBean).setApprovalUrl(approvalUrl);
		verify(paypalBean).setAmount(amount);
		verify(paypalBean).setUserId(userId);
		verify(paypalBean).setPaymentId(paymentId);
	}

	private void setStartPaymentExpectations() throws PayPalRESTException {
		when(paypalCollaborator.setAmount(userId, amount)).thenReturn(payment);
		when(paypalHelper.createRedirectUrl()).thenReturn(redirectUrls);
		when(paypalHelper.createPaypalBean()).thenReturn(paypalBean);
		when(oauthService.getPaypalToken()).thenReturn(token);
	}
	
	@Test 
	public void shouldNotStartPaymentDueToPaypalRestException() throws Exception {
		when(paypalCollaborator.setAmount(userId, amount)).thenReturn(payment);
		when(paypalHelper.createRedirectUrl()).thenReturn(redirectUrls);
		when(paypalHelper.createPaypalBean()).thenReturn(paypalBean);
		when(oauthService.getPaypalToken()).thenThrow(new PayPalRESTException("Invalid Token"));

		paypalService.cashIn(userId, null, null, amount, null, cancelUrl, returnUrl);
		
		verify(payment, never()).create(token);
		verify(paypalBean, never()).setAmount(amount);
		verify(paypalBean, never()).setUserId(userId);
		verify(paypalBean, never()).setPaymentId(paymentId);
	}
	
	@Test 
	public void shouldNotStartPaymentDueToApprovalLinkException() throws Exception {
		setStartPaymentExpectations();
		when(payment.create(token)).thenReturn(createdPayment);
		when(createdPayment.getLinks()).thenReturn(links);
		when(paypalCollaborator.getApprovalLink(links)).thenThrow(new ApprovalLinkException());
		
		paypalService.cashIn(userId, null, null, amount, null, cancelUrl, returnUrl);
		
		verify(paypalBean, never()).setAmount(amount);
		verify(paypalBean, never()).setUserId(userId);
		verify(paypalBean, never()).setPaymentId(paymentId);
	}
	
	@Test
	public void shouldCompletePayment() throws Exception {
		setCompletePaymentExpectations();
		when(paypalBean.getAmount()).thenReturn(amount);
		when(oauthService.getPaypalToken()).thenReturn(paypalToken);
		
		paypalService.completePayment(paypalBean, payerID);
		
		verify(payment).setId(paymentId);
		verify(paymentExecution).setPayerId(payerID);

		verify(payment).execute(paypalToken, paymentExecution);
	}
	
	@Test
	public void shouldNotCompletePaymentDueToPayPalRESTException() throws Exception {
		setCompletePaymentExpectations();
		when(paypalBean.getAmount()).thenReturn(amount);
		when(oauthService.getPaypalToken()).thenThrow(new PayPalRESTException("Invalid Token"));
		
		paypalService.completePayment(paypalBean, payerID);
		
		verify(payment).setId(paymentId);
		verify(paymentExecution).setPayerId(payerID);

		verify(payment, never()).execute(paypalToken, paymentExecution);
	}

	private BigDecimal setCompletePaymentExpectations() {
		BigDecimal expectedBalance = new BigDecimal("7500");
		when(paypalHelper.createPayment()).thenReturn(payment);
		when(paypalBean.getPaymentId()).thenReturn(paymentId);
		when(paypalHelper.createPaymentExecution()).thenReturn(paymentExecution);
		when(paypalBean.getUserId()).thenReturn(userId);
		return expectedBalance;
	}

}
