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
import com.tim.one.bean.mail.ContactBean;
import com.tim.one.command.ContactCommand;
import com.tim.one.integration.MessageService;
import com.tim.one.validator.CommandValidator;

/**
 * @author josdem
 * @understands A class who knows how to send emails using Json
 *
 */

@Controller
@RequestMapping("/valuarte/*")
public class ValuarteController {

	@Autowired
	private MessageService messageDispatcher;
	@Autowired
	private CommandValidator validator;
	@Autowired
	private Properties dynamic;

	private Log log = LogFactory.getLog(getClass());

	@RequestMapping(method = POST, value = "/contact")
	@ResponseBody
	public ResponseEntity<String> forgotPassword(@RequestBody String json){
		ContactCommand command = new Gson().fromJson(json, ContactCommand.class);
		log.info("Sending contact email: " + ToStringBuilder.reflectionToString(command));
		
		if(!validator.isValid(command)){
	    return new ResponseEntity<String>("Error: " + ErrorCode.VALIDATOR_ERROR.ordinal(), HttpStatus.BAD_REQUEST);
		}
		
		ContactBean bean = new ContactBean();
    bean.setEmail("sergio@makingdevs.com");
    bean.setName(command.getName());
    bean.setType(MessageType.CONTACT);
    messageDispatcher.message(bean);
    return new ResponseEntity<String>("OK", HttpStatus.OK);
	}
	
}