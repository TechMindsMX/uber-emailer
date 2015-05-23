package com.tim.one.paypal.collabotator;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import com.tim.one.paypal.exception.ApprovalLinkException;
import com.tim.one.paypal.helper.PaypalHelper;

/**
 * @author josdem
 * @understands A class who helps to PaypalService to organize transactions 
 */

@Component
public class PaypalCollaborator {
	
	@Autowired
	private PaypalHelper paypalHelper;

	public Payment setAmount(Integer userId, BigDecimal userAmount) {
		Amount amount = paypalHelper.createAmount();
		amount.setCurrency("MXN");
		amount.setTotal(userAmount.toString());
		
		Transaction transaction = paypalHelper.createTransaction();
		transaction.setAmount(amount);
		transaction.setDescription("Transfer from: " + userId);
		
		List<Transaction> transactions = paypalHelper.createTransactions();
		transactions.add(transaction);
		
		Payer payer = paypalHelper.createPayer();
		payer.setPaymentMethod("Paypal");
		
		Payment payment = paypalHelper.createPayment();
		payment.setIntent("sale");
		payment.setPayer(payer);
		payment.setTransactions(transactions);
		return payment;
	}

	public String getApprovalLink(List<Links> links) throws ApprovalLinkException {
		String approvalLink = null;
		for (Links link : links) {
			if (link.getRel().equalsIgnoreCase("approval_url")) {
				approvalLink = link.getHref();
			}
		}
		if (approvalLink == null) {
			throw new ApprovalLinkException();
		}
		return approvalLink;
	}

}
