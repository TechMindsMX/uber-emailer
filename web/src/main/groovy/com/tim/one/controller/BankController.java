package com.tim.one.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.tim.one.model.TramaAccount;
import com.tim.one.service.BankService;


@Controller
@RequestMapping("/bank/*")
public class BankController {

	@Autowired
	private BankService bankService;

	private Log log = LogFactory.getLog(getClass());

	@RequestMapping(method = GET, value = "/listAdministrativeAccounts")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Iterable<TramaAccount> listAdministrativeAccounts() {
		log.info("LISTING Administrative accounts");
		return bankService.getAdministrativeAccounts();
	}

}
