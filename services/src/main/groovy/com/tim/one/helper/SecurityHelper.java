package com.tim.one.helper;

import org.springframework.stereotype.Component;

import com.tim.one.model.SessionKey;

@Component
public class SecurityHelper {

	public SessionKey createSessionKey() {
		return new SessionKey();
	}

}
