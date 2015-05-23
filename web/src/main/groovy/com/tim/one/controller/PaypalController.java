package com.tim.one.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.tim.one.command.PaypalPaymentCommand;
import com.tim.one.command.PaypalPaymentCompleteCommand;
import com.tim.one.exception.UserNotFoundException;
import com.tim.one.paypal.bean.PaypalBean;
import com.tim.one.paypal.helper.PaypalHelper;
import com.tim.one.paypal.service.PaypalService;
import com.tim.one.paypal.state.ApplicationState;
import com.tim.one.service.PaypalPaymentStorerService;
import com.tim.one.service.SecurityService;
import com.tim.one.state.ApprovalStorer;
import com.tim.one.state.ErrorState;
import com.tim.one.status.TimoneErrorStatus;
import com.tim.one.util.DateUtil;

@Controller
@RequestMapping("/paypal/*")
public class PaypalController {

	@Autowired
	private PaypalService paypalService;
	@Autowired
	private SecurityService securityService;
	@Autowired
	private PaypalHelper paypalHelper;
	@Autowired
	private DateUtil dateUtil;
	@Autowired
	private PaypalPaymentStorerService paypalPaymentStorerService;

	@Autowired
	private Properties properties;
	@Autowired
	private Properties dynamic;

	private Log log = LogFactory.getLog(getClass());

	@RequestMapping(method = GET, value = "/completePayment")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void completePayment(PaypalPaymentCompleteCommand command, HttpServletResponse response) throws IOException {
		log.info("COMPLETING Paypal transaction w/guid:" + command.getGuid() + " PayerID: " + command.getPayerID());
		PaypalBean paypalBean = ApprovalStorer.APPROVALS.get(command.getGuid());
		log.info("paypalBean: " + ToStringBuilder.reflectionToString(paypalBean));
		try {
			paypalService.completePayment(paypalBean, command.getPayerID());
			paypalBean.setTimestamp(dateUtil.createDateAsLong());
			BigDecimal userBalance = paypalPaymentStorerService.saveUserPayment(paypalBean.getUserId(),
					paypalBean.getAmount());
			ApprovalStorer.APPROVALS.remove(command.getGuid());
			response.sendRedirect(paypalBean.getCallback() + "&balance=" + userBalance + "&amount=" + paypalBean.getAmount()
					+ "&timestamp=" + paypalBean.getTimestamp());
		} catch (UserNotFoundException unfe) {
			log.error(unfe, unfe);
		}
	}
	
	@RequestMapping(method = POST, value = "/payment")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void payment(@Valid PaypalPaymentCommand command, HttpServletResponse response) throws IOException {
		log.info("CALLING Paypal payment");

		if (securityService.isValid(command.getToken())) {
			log.info("SETING Paypal payment");
			String guid = paypalHelper.createGuid();

			log.info("ReturnUrl : " + dynamic.getProperty(ApplicationState.RETURN_URL));
			PaypalBean paypalBean = (PaypalBean) paypalService.cashIn(command.getUserId(), null, null, command.getAmount(), null,
					command.getCallback(), dynamic.getProperty(ApplicationState.RETURN_URL) + guid);

			if (paypalBean.getPaymentId() != null) {
				paypalBean.setCallback(command.getCallback());
				ApprovalStorer.APPROVALS.put(guid, paypalBean);
				response.sendRedirect(paypalBean.getApprovalUrl());
			} else {
				response.sendRedirect(command.getCallback() + ErrorState.getErrorCode(TimoneErrorStatus.PAYPAL_ERROR));
			}
		} else {
			response.sendRedirect(command.getCallback() + ErrorState.getErrorCode(TimoneErrorStatus.TOKEN_EXPIRED));
		}
	}

}