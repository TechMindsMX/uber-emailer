package com.tim.one.controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

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
import com.tim.one.bean.mail.ForgotPasswordBean;
import com.tim.one.command.ForgotPasswordCommand;
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
    bean.setToken(ApplicationState.HOST + command.getToken());
    bean.setEmail(command.getEmail());
    bean.setType(MessageType.FORGOT_PASSWORD);
    messageDispatcher.message(bean);
    return new ResponseEntity<String>("OK", HttpStatus.OK);
	}

}
