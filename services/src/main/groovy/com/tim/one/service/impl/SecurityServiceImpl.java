package com.tim.one.service.impl;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tim.one.bean.KeyStatus;
import com.tim.one.collaborator.SecurityCollaborator;
import com.tim.one.helper.SecurityHelper;
import com.tim.one.model.SessionKey;
import com.tim.one.repository.SecurityRepository;
import com.tim.one.service.SecurityService;
import com.tim.one.state.ApplicationState;
import com.tim.one.util.DateUtil;

/**
 * @author josdem
 * @understands A class who knows how manage security issues
 * 
 */

@Service
public class SecurityServiceImpl implements SecurityService {
	
	@Autowired
	private DateUtil dateUtil;
	@Autowired
	private SecurityCollaborator securityCollaborator;
	@Autowired
	private SecurityRepository securityDao;
	@Autowired
	private SecurityHelper securityHelper;
	@Autowired
	@Qualifier("properties")
	private Properties properties;
	
	public String generateKey() {
		String key = securityCollaborator.generateKey();
		Long timestamp = dateUtil.createDateAsLong();
		SessionKey sessionKey = securityHelper.createSessionKey();
		sessionKey.setApiKey(key);
		sessionKey.setTimestamp(timestamp);
		sessionKey.setKeyStatus(KeyStatus.VALID);
		securityDao.save(sessionKey);
		return key;
	}

	public boolean isValid(String token) {
		if (token == null) return false;
		SessionKey sessionKey = securityDao.findByApiKey(token);
		if(sessionKey != null){
			sessionKey.setKeyStatus(KeyStatus.INVALID);
			securityDao.save(sessionKey);
			return (dateUtil.createDateAsLong() - sessionKey.getTimestamp() < ApplicationState.MILISECONDS * ApplicationState.TIME_UNIT * new Integer(properties.getProperty(ApplicationState.TIMEOUT))) ? true : false;
		}
		return false;
	}

}
