package com.tim.one.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.tim.one.integra.command.IntegraUserCommand;
import com.tim.one.model.IntegraUser;
import com.tim.one.service.IntegraUserService;
import com.tim.one.validator.CommandValidator;

/**
 * @author josdem
 * @understands A class who knows how receive manage User request
 *              forward to the service and return to the client
 */

@Controller
@RequestMapping("/integra/*")
public class IntegraUserController {

	@Autowired
	private IntegraUserService userService;
	@Autowired 
	private CommandValidator validator;

	private Log log = LogFactory.getLog(getClass());

	@RequestMapping(method = GET, value = "/users/{uuid}")
	@ResponseBody
	public IntegraUser getByUuid(IntegraUserCommand command){
		log.info("LISTING User by:" + command.getUuid());
		return userService.getByUuid(command.getUuid());
	}
	
	@RequestMapping(method = POST, value = "/users")
	@ResponseBody
	public ResponseEntity<IntegraUser> create(@RequestBody String json){
		IntegraUserCommand command = new Gson().fromJson(json, IntegraUserCommand.class);
		log.info("CREATING User w/uuid: " + command.getUuid());
		if(!validator.isValid(command)){
			return new ResponseEntity<IntegraUser>(new IntegraUser(), HttpStatus.BAD_REQUEST);
		}
		IntegraUser user = userService.create(command.getUuid(), command.getName(), command.getEmail());
		return new ResponseEntity<IntegraUser>(user, HttpStatus.OK);
	}
	
}
