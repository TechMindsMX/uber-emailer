package com.tim.one.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.tim.one.service.StatusService;

/**
 * @author josdem
 * @understands A class who knows how list project's status
 *
 */

@Controller
@RequestMapping("/status/*")
public class StatusController {

	@Autowired
	private StatusService statusService;

	private Log log = LogFactory.getLog(getClass());

	@RequestMapping(method = GET, value = "/list")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Map<Integer, String>> listProjectStatus() {
		log.info("LISTING Project's status");
		return statusService.getProjectStatus();
	}

}
