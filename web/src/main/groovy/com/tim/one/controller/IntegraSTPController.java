package com.tim.one.controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.gson.Gson;
import com.tim.one.exception.BusinessException;
import com.tim.one.integra.command.IntegraCashoutCommand;
import com.tim.one.service.STPConsumerService;
import com.tim.one.validator.CommandValidator;

@Component
@RequestMapping("/integra/stp/*")
public class IntegraSTPController {

	@Autowired
	private STPConsumerService stpService;
	@Autowired 
	private CommandValidator validator;

	private Log log = LogFactory.getLog(getClass());

	@RequestMapping(method = POST, value = "/cashout")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> cashOut(@RequestBody String json) {
		IntegraCashoutCommand command = new Gson().fromJson(json, IntegraCashoutCommand.class);
		log.info("CALLING integra cashout");
		try{
			if(!validator.isValid(command)){
				return new ResponseEntity<String>("Validation error", HttpStatus.BAD_REQUEST);
			}
			String stringUuid = stpService.cashOut(command.getUuid(), command.getClabe(), command.getBankCode(), command.getAmount());
			return new ResponseEntity<String>(stringUuid, HttpStatus.OK);
		} catch (BusinessException be) {
			log.warn(be, be);
			return new ResponseEntity<String>(be.getMessage(), HttpStatus.UNAUTHORIZED);
		} 
	} 
	
}
