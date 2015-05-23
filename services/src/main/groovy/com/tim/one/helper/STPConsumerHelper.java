package com.tim.one.helper;

import org.springframework.stereotype.Component;

import com.tim.one.model.StpStatus;
import com.tim.one.model.UserAccount;

@Component
public class STPConsumerHelper {
	
	public StpStatus createSTPStatus() {
		return new StpStatus();
	}
	
	public UserAccount createUserAccount() {
		return new UserAccount();
	}

}
