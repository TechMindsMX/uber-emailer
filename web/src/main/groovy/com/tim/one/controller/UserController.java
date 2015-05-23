package com.tim.one.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.tim.one.collaborator.UserCollaborator;
import com.tim.one.command.UserCommand;
import com.tim.one.command.UserSaveCommand;
import com.tim.one.exception.NotValidParameterException;
import com.tim.one.model.User;
import com.tim.one.service.SecurityService;
import com.tim.one.service.UserService;

/**
 * @author josdem
 * @understands A class who knows how receive manage User request
 *              forward to the service and return to the client
 */

@Controller
@RequestMapping("/user/*")
public class UserController {

	@Autowired
	private SecurityService securityService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserCollaborator userCollaborator;

	private Log log = LogFactory.getLog(getClass());

	@RequestMapping(method = GET, value = "/get/{userId}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public User getUserByEmail(UserCommand command) {
		log.info("GETTING User by id: " + command.getUserId());
		return userService.getUserById(command.getUserId());
	}

	@RequestMapping(method = GET, value = "/getByAccount/{account}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public User getByAccount(UserCommand command) {
		log.info("GETTING User by account: " + command.getAccount());
		return userService.getUserByAccount(command.getAccount());
	}

	@RequestMapping(method = POST, value = "/saveUser")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ResponseEntity<User> createUser(@Valid UserSaveCommand command){
		log.info("TRYING User w/email: " + command.getEmail() + " name: " + command.getName() + " token: " + command.getToken());

		if (securityService.isValid(command.getToken())){
			log.info("SAVING User w/email: " + command.getEmail());
			try{
				User user = userService.getUserByEmail(command.getEmail());
				if(user == null) {
					user = userCollaborator.createUser(command.getEmail(), command.getName());
				}
				return new ResponseEntity<User>(user, HttpStatus.OK);
			} catch (NotValidParameterException nvpe) {
				log.warn(nvpe, nvpe);
				return new ResponseEntity<User>(new User(), HttpStatus.UNAUTHORIZED);
			}
		}

		return new ResponseEntity<User>(new User(), HttpStatus.BAD_REQUEST);
	}
}
