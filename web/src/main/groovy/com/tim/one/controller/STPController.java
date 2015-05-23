package com.tim.one.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.tim.one.command.StpCashInCommand;
import com.tim.one.command.StpConfirmOrderCommand;
import com.tim.one.command.StpRegisterOrderCommand;
import com.tim.one.exception.AccountNotFoundException;
import com.tim.one.exception.BusinessException;
import com.tim.one.exception.NonSufficientFundsException;
import com.tim.one.model.BankCode;
import com.tim.one.service.STPConsumerService;
import com.tim.one.service.SecurityService;
import com.tim.one.state.ErrorState;
import com.tim.one.state.ResponseState;
import com.tim.one.status.TimoneErrorStatus;
import com.tim.one.stp.exception.SPEITransactionException;
import com.tim.one.stp.service.RestService;

@Component
@RequestMapping("/stp/*")
public class STPController {

	@Autowired
	private STPConsumerService stpService;
	@Autowired
	private SecurityService securityService;
	@Autowired
	private RestService restService;

	private Log log = LogFactory.getLog(getClass());
	
	@RequestMapping(method = GET, value = "/listBankCodes")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Iterable<BankCode> listBankCodes() {
		log.info("LISTING bank codes ");
		return stpService.listBankCodes();
	}

	@RequestMapping(method = POST, value = "/registraOrden")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void cashOut(@Valid StpRegisterOrderCommand command, HttpServletResponse response) throws IOException {
		log.info("CALLING registra orden");

		if (securityService.isValid(command.getToken())){
			Integer speiId;
			try {
				log.info("REGISTRING registra orden");
				speiId = stpService.cashOut(command.getUserId(), command.getAmount());
				response.sendRedirect(command.getCallback() + ResponseState.getResponse(speiId));
			} catch (AccountNotFoundException anfe) {
				log.warn(anfe, anfe);
				response.sendRedirect(command.getCallback() + ErrorState.getErrorCode(TimoneErrorStatus.ACCOUNT_NOT_FOUND));
			} catch (NonSufficientFundsException nsfe) {
				log.warn(nsfe, nsfe);
				response.sendRedirect(command.getCallback() + ErrorState.getErrorCode(TimoneErrorStatus.NON_SUFFICIENT_FUNDS));
			} catch (SPEITransactionException ste) {
				log.warn(ste, ste);
				response.sendRedirect(command.getCallback() + ErrorState.getErrorCode(TimoneErrorStatus.STP_TRANSACTION_ERROR));
			}
		} else {
			response.sendRedirect(command.getCallback() + ErrorState.getErrorCode(TimoneErrorStatus.TOKEN_EXPIRED));
		}
	}

	@RequestMapping(method = POST, value = "/cashIn")
	public ResponseEntity<String> cashIn(@Valid StpCashInCommand command, HttpServletRequest request) throws URISyntaxException {
		log.info("IP Request: " + request.getRemoteHost());		
		log.info("REGISTRING to transfer funds from CLABE:" + command.getClabeOrdenante() + " to Trama w/amount: " + command.getAmount() + " reference: " + command.getReference() + " token: " + command.getToken());
		
		if (securityService.isValid(command.getToken())){
			try{
				stpService.cashIn(null, command.getClabeOrdenante(), command.getClabeBeneficiario(), command.getAmount(), command.getReference(), null, null);
				return new ResponseEntity<String>("OK", HttpStatus.OK);
			} catch (BusinessException be) {
				return new ResponseEntity<String>(be.getMessage(), HttpStatus.UNAUTHORIZED);
			}
		} else {
			return new ResponseEntity<String>(TimoneErrorStatus.TOKEN_EXPIRED.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = POST, value = "/confirmaOrden")
	public ResponseEntity<String> confirmaOrden(@Valid StpConfirmOrderCommand command) throws URISyntaxException {

		log.info("CONFIRMING speiId: " + command.getId());

		if (securityService.isValid(command.getToken())){
			try {
				stpService.confirmaOrden(command.getId(), command.getClaveRastreo(), command.getEstado(), command.getTimestamp());
				return new ResponseEntity<String>("OK", HttpStatus.OK);
			} catch (BusinessException be) {
				return new ResponseEntity<String>("ERROR", HttpStatus.UNAUTHORIZED);
			}
		} else {
			return new ResponseEntity<String>("ERROR", HttpStatus.BAD_REQUEST);
		}
	}

}
