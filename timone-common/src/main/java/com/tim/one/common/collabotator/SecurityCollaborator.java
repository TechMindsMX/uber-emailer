package com.tim.one.common.collabotator;

import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tim.one.common.util.DateUtil;

/**
 * @author josdem
 * @understands A class who knows how generate and validate security token
 *
 */

@Component
public class SecurityCollaborator {
	
	@Autowired
	private DateUtil dateUtil;
	
	private Log log = LogFactory.getLog(getClass());
	
	public String generateKey() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	public Boolean isTokenValue(String value){
		Long timestamp = Long.parseLong(value);
		Long now = dateUtil.createDateAsLong();
		long diff = now - timestamp;
		log.info("diff: " + diff);
		return diff < 1000 ;
	}

}
