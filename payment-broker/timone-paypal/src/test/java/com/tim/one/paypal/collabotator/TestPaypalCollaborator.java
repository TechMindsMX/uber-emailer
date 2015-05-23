package com.tim.one.paypal.collabotator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import com.tim.one.paypal.collabotator.PaypalCollaborator;
import com.tim.one.paypal.exception.ApprovalLinkException;
import com.tim.one.paypal.helper.PaypalHelper;

public class TestPaypalCollaborator {

	@InjectMocks
	private PaypalCollaborator paypalCollaborator = new PaypalCollaborator();
	
	@Mock
	private PaypalHelper paypalHelper;
	@Mock
	private Amount amount;
	@Mock
	private Transaction transaction;
	@Mock
	private List<Transaction> transactions;
	@Mock
	private Payer payer;
	@Mock
	private Payment payment;
	@Mock
	private Links link;

	private String approvalUrl = "approvalUrl";
	private BigDecimal userAmount = new BigDecimal("1000");

	private List<Links> links = new ArrayList<Links>();

	private Integer userId = 1;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldSetAmount() throws Exception {
		when(paypalHelper.createAmount()).thenReturn(amount);
		when(paypalHelper.createTransaction()).thenReturn(transaction);
		when(paypalHelper.createTransactions()).thenReturn(transactions);
		when(paypalHelper.createPayer()).thenReturn(payer);
		when(paypalHelper.createPayment()).thenReturn(payment);
		
		paypalCollaborator.setAmount(userId , userAmount);
		
		verify(amount).setCurrency("MXN");
		verify(amount).setTotal(userAmount.toString());
		verify(transaction).setAmount(amount);
		verify(transaction).setDescription("Transfer from: " + userId);
		verify(transactions).add(transaction);
		verify(payer).setPaymentMethod("Paypal");
		verify(payment).setIntent("sale");
		verify(payment).setPayer(payer);
		verify(payment).setTransactions(transactions);
	}
	
	@Test
	public void shouldGetApprovalLink() throws Exception {
		links.add(link);
		
		when(link.getRel()).thenReturn("approval_url");
		when(link.getHref()).thenReturn(approvalUrl);
		
		assertEquals(approvalUrl, paypalCollaborator.getApprovalLink(links));
	}
	
	@Test (expected=ApprovalLinkException.class)
	public void shouldNotGetApprovalLink() throws Exception {
		paypalCollaborator.getApprovalLink(links);
	}

}
