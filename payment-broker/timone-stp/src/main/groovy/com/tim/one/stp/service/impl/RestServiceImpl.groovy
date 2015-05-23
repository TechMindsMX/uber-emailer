package com.tim.one.stp.service.impl

import javax.annotation.PostConstruct

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

import com.tim.one.stp.service.RestService
import com.tim.one.stp.state.ApplicationState

/**
 * @author josdem
 * @understands A class who knows how to manage rest request/responses
 * 
 */

@Service
class RestServiceImpl implements RestService {
	
	@Autowired
	RestTemplate restTemplate
	
	@Autowired
	@Qualifier("stpProperties")
	Properties properties

	String integradoraUrl
	
	Log log = LogFactory.getLog(getClass())
	
	@PostConstruct
	public void setup(){
		integradoraUrl = properties.getProperty(ApplicationState.INTEGRADORA_URL)
	}

	@Override
	public String postForObject(String jsonToSend) {
		log.info("integradoraUrl: " + integradoraUrl);
		log.info("jsonToSend:" + jsonToSend)
		
		HttpHeaders headers = new HttpHeaders()
		headers.setContentType(MediaType.APPLICATION_JSON)
		HttpEntity<String> request= new HttpEntity<String>(jsonToSend, headers)
		
		String result = restTemplate.postForObject(integradoraUrl, request, String.class)
		log.info("result:" + result)

		return result
	}
	
	
}
