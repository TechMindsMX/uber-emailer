package com.tim.one.helper;

import org.springframework.stereotype.Component;

import com.tim.one.model.User;

@Component
public class UserHelper {
	
	public User createUser() {
		return new User();
	}

}
