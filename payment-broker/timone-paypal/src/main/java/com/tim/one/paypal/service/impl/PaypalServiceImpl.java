package com.tim.one.paypal.service.impl;

import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.core.rest.PayPalRESTException;
import com.tim.one.paypal.bean.PaypalBean;
import com.tim.one.paypal.collabotator.PaypalCollaborator;
import com.tim.one.paypal.exception.ApprovalLinkException;
import com.tim.one.paypal.helper.PaypalHelper;
import com.tim.one.paypal.service.OauthService;
import com.tim.one.paypal.service.PaypalService;

@Service
public class PaypalServiceImpl implements PaypalService {
	
	@Autowired
	private OauthService oauthService;
	@Autowired
	private PaypalCollaborator paypalCollaborator;
	@Autowired
	private PaypalHelper paypalHelper;
	
	private Log log = LogFactory.getLog(getClass());
	
	public PaypalBean cashIn(Integer userId, String clabeOrdenante, String clabeBeneficiario, BigDecimal amount, String reference, String cancelUrl, String returnUrl){
		Payment payment = paypalCollaborator.setAmount(userId, amount);
		
		RedirectUrls redirectUrls = paypalHelper.createRedirectUrl();
		redirectUrls.setCancelUrl(cancelUrl);
		redirectUrls.setReturnUrl(returnUrl);
		
		payment.setRedirectUrls(redirectUrls);

		PaypalBean paypalBean = paypalHelper.createPaypalBean();
		try{
			String token = oauthService.getPaypalToken();
			Payment createdPayment = payment.create(token);
			paypalBean.setApprovalUrl(paypalCollaborator.getApprovalLink(createdPayment.getLinks()));
			paypalBean.setAmount(amount);
			paypalBean.setUserId(userId);
			paypalBean.setPaymentId(createdPayment.getId());
		} catch (PayPalRESTException pre){
			log.error(pre, pre);
		} catch (ApprovalLinkException ale) {
			log.error(ale, ale);
		}
		return paypalBean;
	}

	public void completePayment(PaypalBean paypalBean, String payerID) {
		Payment payment = paypalHelper.createPayment();
		payment.setId(paypalBean.getPaymentId());

		PaymentExecution paymentExecution = paypalHelper.createPaymentExecution();
		paymentExecution.setPayerId(payerID);
		try {
			String paypalToken = oauthService.getPaypalToken();
			payment.execute(paypalToken, paymentExecution);
			log.info("response: " + Payment.getLastResponse());
		} catch (PayPalRESTException pre) {
			log.error(pre, pre);
		} 
	}

  @Override
  public Integer cashOut(Integer userId, BigDecimal amount) {
    throw new UnsupportedOperationException();
  }

	@Override
	public String cashOut(String uuid, String clabe, Integer bankCode, BigDecimal amount) {
		throw new UnsupportedOperationException();
	}

}
