package com.tim.one.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.tim.one.service.CatalogService;

@Controller
@RequestMapping("/catalog/*")
public class CatalogController {

	@Autowired
	private CatalogService catalogService;

	private Log log = LogFactory.getLog(getClass());

	@RequestMapping(method = GET, value = "/closedReasons")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<String> getClosedReason() {
		log.info("LISTING Project's closed reason");
		return catalogService.getClosedReason();
	}

}
