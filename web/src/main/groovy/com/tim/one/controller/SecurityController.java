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

import com.tim.one.service.SecurityService;

@Controller
@RequestMapping("/security/*")
public class SecurityController {

	@Autowired
	private SecurityService securityService;

	private Log log = LogFactory.getLog(getClass());

	@RequestMapping(method = GET, value = "getKey")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public String getKey() {
		log.info("GETTING key");
		return securityService.generateKey();
	}

}



