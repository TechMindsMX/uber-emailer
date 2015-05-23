package com.tim.one.paypal.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.tim.one.paypal.bean.PaypalBean;

@Component
public class PaypalHelper {

	public String createGuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public RedirectUrls createRedirectUrl() {
		return new RedirectUrls();
	}

	public PaypalBean createPaypalBean() {
		return new PaypalBean();
	}

	public Payment createPayment() {
		return new Payment();
	}

	public PaymentExecution createPaymentExecution() {
		return new PaymentExecution();
	}

	public Amount createAmount() {
		return new Amount();
	}

	public Transaction createTransaction() {
		return new Transaction();
	}

	public List<Transaction> createTransactions() {
		return new ArrayList<Transaction>();
	}

	public Payer createPayer() {
		return new Payer();
	}
	
}
