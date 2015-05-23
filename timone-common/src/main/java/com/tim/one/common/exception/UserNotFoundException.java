package com.tim.one.common.exception;


public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 637289683907874466L;
	
	private Integer userId;
	
	public UserNotFoundException(Integer userId) {
		this.userId = userId;
	}

	@Override
	public String getMessage() {
		return "I CAN NOT TRANSFER FUNDS TO THIS: " + userId + " SINCE IT DOES NOT EXIST";
	}

}
