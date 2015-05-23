package com.tim.one.collaborator;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

/**
 * @author josdem
 * @understands A class who knows how receive a form parameters values
 *              and can convert their values to a Java conventional values
 */

@Component
public class FormParamFormatter {
	
	private Log log = LogFactory.getLog(getClass());

	public Map<String, String> formatParams(MultiValueMap<String, String> formParams) {
		Map<String, String> params = new HashMap<String, String>();
		for (String key : formParams.keySet()) {
			log.info("=key: " + key + " value: " + formParams.get(key).get(0));
			params.put(key, formParams.get(key).get(0));
		}
		return params;
	}

}
