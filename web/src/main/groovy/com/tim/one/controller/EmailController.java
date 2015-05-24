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
import com.tim.one.bean.MessageType;
import com.tim.one.bean.mail.AbonoCuentaBean;
import com.tim.one.command.EmailCommand;
import com.tim.one.integration.MessageService;

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

	private Log log = LogFactory.getLog(getClass());

	@RequestMapping(method = POST, value = "/send")
	@ResponseBody
	public ResponseEntity<String> send(@RequestBody String json) {
		EmailCommand command = new Gson().fromJson(json, EmailCommand.class);
		log.info("Sending email: " + ToStringBuilder.reflectionToString(command));
		
    AbonoCuentaBean bean = new AbonoCuentaBean();
    bean.setAmount("10.00");
    bean.setName("josdem");
    bean.setDate("today");
    bean.setId("D1232134");
    bean.setEmail("joseluis.delacruz@gmail.com");
    bean.setType(MessageType.ABONO_CUENTA);
    messageDispatcher.message(bean);
    return new ResponseEntity<String>("OK", HttpStatus.OK);
	}

}
