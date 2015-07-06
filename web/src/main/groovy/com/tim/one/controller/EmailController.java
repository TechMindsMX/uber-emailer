package com.tim.one.controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Properties;

import org.apache.commons.lang.builder.ToStringBuilder;
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
import com.tim.one.bean.ErrorCode;
import com.tim.one.bean.MessageType;
import com.tim.one.bean.mail.AssignationBean;
import com.tim.one.bean.mail.ForgotPasswordBean;
import com.tim.one.bean.mail.NewUserBean;
import com.tim.one.command.AssignationCommand;
import com.tim.one.command.ForgotPasswordCommand;
import com.tim.one.command.NewUserCommand;
import com.tim.one.command.RegisterCommand;
import com.tim.one.integration.MessageService;
import com.tim.one.state.ApplicationState;
import com.tim.one.validator.CommandValidator;

/**
 * @author josdem
 * @understands A class who knows how to send emails using Json
 *
 */

@Controller
@RequestMapping("/email/*")
public class EmailController {

	@Autowired
	private MessageService messageDispatcher;
	@Autowired
	private CommandValidator validator;
	@Autowired
	private Properties dynamic;

	private Log log = LogFactory.getLog(getClass());

	@RequestMapping(method = POST, value = "/forgotPassword")
	@ResponseBody
	public ResponseEntity<String> forgotPassword(@RequestBody String json){
		ForgotPasswordCommand command = new Gson().fromJson(json, ForgotPasswordCommand.class);
		log.info("Sending email: " + ToStringBuilder.reflectionToString(command));
		
		if(!validator.isValid(command)){
	    return new ResponseEntity<String>("Error: " + ErrorCode.VALIDATOR_ERROR.ordinal(), HttpStatus.BAD_REQUEST);
		}
		
    ForgotPasswordBean bean = new ForgotPasswordBean();
    bean.setToken(dynamic.getProperty(ApplicationState.FORGOT_PASSWORD_PREFIX) + command.getToken());
    bean.setEmail(command.getEmail());
    bean.setType(MessageType.FORGOT_PASSWORD);
    messageDispatcher.message(bean);
    return new ResponseEntity<String>("OK", HttpStatus.OK);
	}
	
	@RequestMapping(method = POST, value = "/register")
	@ResponseBody
	public ResponseEntity<String> register(@RequestBody String json){
		RegisterCommand command = new Gson().fromJson(json, RegisterCommand.class);
		log.info("Sending email: " + ToStringBuilder.reflectionToString(command));
		
		if(!validator.isValid(command)){
	    return new ResponseEntity<String>("Error: " + ErrorCode.VALIDATOR_ERROR.ordinal(), HttpStatus.BAD_REQUEST);
		}
		
    ForgotPasswordBean bean = new ForgotPasswordBean();
    bean.setToken(dynamic.getProperty(ApplicationState.REGISTER_PREFIX) + command.getToken());
    bean.setEmail(command.getEmail());
    bean.setType(MessageType.REGISTER);
    messageDispatcher.message(bean);
    return new ResponseEntity<String>("OK", HttpStatus.OK);
	}
	
	@RequestMapping(method = POST, value = "/newUser")
	@ResponseBody
	public ResponseEntity<String> newUser(@RequestBody String json){
		NewUserCommand command = new Gson().fromJson(json, NewUserCommand.class);
		log.info("Sending email: " + ToStringBuilder.reflectionToString(command));
		
		if(!validator.isValid(command)){
	    return new ResponseEntity<String>("Error: " + ErrorCode.VALIDATOR_ERROR.ordinal(), HttpStatus.BAD_REQUEST);
		}
		
    NewUserBean bean = new NewUserBean();
    bean.setEmail(dynamic.getProperty(ApplicationState.ADMIN_EMAIL));
    bean.setName(command.getName());
    bean.setType(MessageType.NEW_USER);
    messageDispatcher.message(bean);
    return new ResponseEntity<String>("OK", HttpStatus.OK);
	}
	
	@RequestMapping(method = POST, value = "/forgotUsername")
	@ResponseBody
	public ResponseEntity<String> forgotUsername(@RequestBody String json){
		NewUserCommand command = new Gson().fromJson(json, NewUserCommand.class);
		log.info("Sending email: " + ToStringBuilder.reflectionToString(command));
		
		if(!validator.isValid(command)){
	    return new ResponseEntity<String>("Error: " + ErrorCode.VALIDATOR_ERROR.ordinal(), HttpStatus.BAD_REQUEST);
		}
		
    NewUserBean bean = new NewUserBean();
    bean.setEmail(command.getEmail());
    bean.setName(command.getName());
    bean.setType(MessageType.FORGOT_USERNAME);
    messageDispatcher.message(bean);
    return new ResponseEntity<String>("OK", HttpStatus.OK);
	}
	
	@RequestMapping(method = POST, value = "/musicianAssignedFacilitator")
	@ResponseBody
	public ResponseEntity<String> musicianAssignedFacilitator(@RequestBody String json){
		AssignationCommand command = new Gson().fromJson(json, AssignationCommand.class);
		log.info("Sending email: " + ToStringBuilder.reflectionToString(command));
		
		if(!validator.isValid(command)){
	    return new ResponseEntity<String>("Error: " + ErrorCode.VALIDATOR_ERROR.ordinal(), HttpStatus.BAD_REQUEST);
		}
		
    AssignationBean bean = new AssignationBean();
    bean.setEmail(command.getEmail());
    bean.setName(command.getName());
    bean.setReference(command.getReference());
    bean.setType(MessageType.MUSICIAN_ASSIGNED_FACILITATOR);
    messageDispatcher.message(bean);
    return new ResponseEntity<String>("OK", HttpStatus.OK);
	}
	
	@RequestMapping(method = POST, value = "/musicianAssignedUser")
	@ResponseBody
	public ResponseEntity<String> musicianAssignedUser(@RequestBody String json){
		AssignationCommand command = new Gson().fromJson(json, AssignationCommand.class);
		log.info("Sending email: " + ToStringBuilder.reflectionToString(command));
		
		if(!validator.isValid(command)){
	    return new ResponseEntity<String>("Error: " + ErrorCode.VALIDATOR_ERROR.ordinal(), HttpStatus.BAD_REQUEST);
		}
		
    AssignationBean bean = new AssignationBean();
    bean.setEmail(command.getEmail());
    bean.setName(command.getName());
    bean.setReference(command.getReference());
    bean.setEmailOptional(command.getEmailOptional());
    bean.setType(MessageType.MUSICIAN_ASSIGNED_USER);
    messageDispatcher.message(bean);
    return new ResponseEntity<String>("OK", HttpStatus.OK);
	}
	
	@RequestMapping(method = POST, value = "/musicianRefused")
	@ResponseBody
	public ResponseEntity<String> musicianRefused(@RequestBody String json){
		AssignationCommand command = new Gson().fromJson(json, AssignationCommand.class);
		log.info("Sending email: " + ToStringBuilder.reflectionToString(command));
		
		if(!validator.isValid(command)){
	    return new ResponseEntity<String>("Error: " + ErrorCode.VALIDATOR_ERROR.ordinal(), HttpStatus.BAD_REQUEST);
		}
		
    AssignationBean bean = new AssignationBean();
    bean.setEmail(command.getEmail());
    bean.setName(command.getName());
    bean.setReference(command.getReference());
    bean.setType(MessageType.MUSICIAN_REFUSED);
    messageDispatcher.message(bean);
    return new ResponseEntity<String>("OK", HttpStatus.OK);
	}
	
	@RequestMapping(method = POST, value = "/companyAssignedBuyer")
	@ResponseBody
	public ResponseEntity<String> companyAssignedBuyer(@RequestBody String json){
		AssignationCommand command = new Gson().fromJson(json, AssignationCommand.class);
		log.info("Sending email: " + ToStringBuilder.reflectionToString(command));
		
		if(!validator.isValid(command)){
	    return new ResponseEntity<String>("Error: " + ErrorCode.VALIDATOR_ERROR.ordinal(), HttpStatus.BAD_REQUEST);
		}
		
    AssignationBean bean = new AssignationBean();
    bean.setEmail(command.getEmail());
    bean.setName(command.getName());
    bean.setReference(command.getReference());
    bean.setType(MessageType.COMPANY_ASSIGNED_BUYER);
    messageDispatcher.message(bean);
    return new ResponseEntity<String>("OK", HttpStatus.OK);
	}
	
	@RequestMapping(method = POST, value = "/companyAssignedUser")
	@ResponseBody
	public ResponseEntity<String> companyAssignedUser(@RequestBody String json){
		AssignationCommand command = new Gson().fromJson(json, AssignationCommand.class);
		log.info("Sending email: " + ToStringBuilder.reflectionToString(command));
		
		if(!validator.isValid(command)){
	    return new ResponseEntity<String>("Error: " + ErrorCode.VALIDATOR_ERROR.ordinal(), HttpStatus.BAD_REQUEST);
		}
		
    AssignationBean bean = new AssignationBean();
    bean.setEmail(command.getEmail());
    bean.setName(command.getName());
    bean.setReference(command.getReference());
    bean.setEmailOptional(command.getEmailOptional());
    bean.setType(MessageType.COMPANY_ASSIGNED_USER);
    messageDispatcher.message(bean);
    return new ResponseEntity<String>("OK", HttpStatus.OK);
	}
	
	@RequestMapping(method = POST, value = "/companyRefused")
	@ResponseBody
	public ResponseEntity<String> companyRefused(@RequestBody String json){
		AssignationCommand command = new Gson().fromJson(json, AssignationCommand.class);
		log.info("Sending email: " + ToStringBuilder.reflectionToString(command));
		
		if(!validator.isValid(command)){
	    return new ResponseEntity<String>("Error: " + ErrorCode.VALIDATOR_ERROR.ordinal(), HttpStatus.BAD_REQUEST);
		}
		
    AssignationBean bean = new AssignationBean();
    bean.setEmail(command.getEmail());
    bean.setName(command.getName());
    bean.setReference(command.getReference());
    bean.setType(MessageType.COMPANY_REFUSED);
    messageDispatcher.message(bean);
    return new ResponseEntity<String>("OK", HttpStatus.OK);
	}

}
