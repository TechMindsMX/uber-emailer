package com.tim.one.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.tim.one.command.AccountCommand;
import com.tim.one.command.CreateAccountCommand;
import com.tim.one.command.TransferAccountCommand;
import com.tim.one.exception.InvalidAmountException;
import com.tim.one.exception.NoAccountExistException;
import com.tim.one.exception.NonSufficientFundsException;
import com.tim.one.exception.UserNotFoundException;
import com.tim.one.model.UserAccount;
import com.tim.one.service.AccountService;
import com.tim.one.service.STPAccountService;
import com.tim.one.service.SecurityService;
import com.tim.one.state.ErrorState;
import com.tim.one.status.TimoneErrorStatus;
import com.tim.one.stp.exception.DuplicatedAccountException;
import com.tim.one.stp.exception.InvalidClabeException;

@Controller
@RequestMapping("/account/*")
public class AccountController {

	@Autowired
	private AccountService accountService;
	@Autowired
	private STPAccountService stpAccountService;
	@Autowired
	private SecurityService securityService;

	private Log log = LogFactory.getLog(getClass());

	@RequestMapping(method = GET, value = "/get/{account}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public String getAccount(AccountCommand command) {
		log.info("LISTING Account owner's name");
		return accountService.getAccount(command.getAccount());
	}

	@RequestMapping(method = GET, value = "/get/account/{userId}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public UserAccount getAccountByUserId(AccountCommand command) {
		log.info("GETTING user's account from userId: " + command.getUserId());
		return stpAccountService.getAccountByUserId(command.getUserId());
	}

	@RequestMapping(method = POST, value = "/transferFundsFromTrama")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void transferFundsFromTrama(@Valid TransferAccountCommand command, HttpServletResponse response) throws IOException {

		log.info("TRYING to transfer funds from Trama to account:" + command.getAccount() + "w/amount: "
				+ command.getAmount());
		if (securityService.isValid(command.getToken())) {
			log.info("TRANSFER funds from Trama to Project or User account:" + command.getAccount() + "w/amount: "
					+ command.getAmount());
			try {
				accountService.transferFundsFromTramaTo(command.getAccount(), command.getAmount());
				response.sendRedirect(command.getCallback());
			} catch (NonSufficientFundsException nsfe) {
				log.warn(nsfe, nsfe);
				response.sendRedirect(command.getCallback() + ErrorState.getErrorCode(TimoneErrorStatus.NON_SUFFICIENT_FUNDS));
			} catch (NoAccountExistException naee) {
				log.warn(naee, naee);
				response.sendRedirect(command.getCallback()
						+ ErrorState.getErrorCode(TimoneErrorStatus.ACCOUNT_NUMBER_NOT_VALID));
			} catch (InvalidAmountException iae) {
				log.warn(iae, iae);
				response.sendRedirect(command.getCallback() + ErrorState.getErrorCode(TimoneErrorStatus.INVALID_AMOUNT));
			}
		} else {
			response.sendRedirect(command.getCallback() + ErrorState.getErrorCode(TimoneErrorStatus.TOKEN_EXPIRED));
		}
	}

	@RequestMapping(method = POST, value = "/createAccount")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void createAccount(@Valid CreateAccountCommand command, HttpServletResponse response) throws IOException {

		log.info("TRYING to create account w/clabe:" + command.getClabe() + " w/bankCode: " + command.getBankCode());
		if (securityService.isValid(command.getToken())) {
			log.info("CREATING to create account w/clabe:" + command.getClabe() + " w/bankCode: " + command.getBankCode());
			try {
				stpAccountService.createAccout(command.getUserId(), command.getBankCode(), command.getClabe());
				response.sendRedirect(command.getCallback());
			} catch (UserNotFoundException unfe) {
				log.warn(unfe, unfe);
				response.sendRedirect(command.getCallback() + ErrorState.getErrorCode(TimoneErrorStatus.USER_NOT_FOUND));
			} catch (DuplicatedAccountException dae) {
				log.warn(dae, dae);
				response.sendRedirect(command.getCallback()
						+ ErrorState.getErrorCode(TimoneErrorStatus.CLABE_ACCOUNT_DUPLICATE));
			} catch (InvalidClabeException ice) {
				log.warn(ice, ice);
				response.sendRedirect(command.getCallback() + ErrorState.getErrorCode(TimoneErrorStatus.INVALID_CLABE_ACCOUNT));
			}
		} else {
			response.sendRedirect(command.getCallback() + ErrorState.getErrorCode(TimoneErrorStatus.TOKEN_EXPIRED));
		}
	}

}
