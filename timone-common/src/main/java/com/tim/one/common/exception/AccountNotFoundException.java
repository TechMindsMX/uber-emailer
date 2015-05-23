package com.tim.one.common.exception;

public class AccountNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4263105970535122953L;
	
	private Integer userId;
	
	public AccountNotFoundException(Integer userId) {
		this.userId = userId;
	}
	
	@Override
	public String getMessage() {
		return "I COULD NOT FIND ANY ACCOUNT TO USER : " + userId;
	}

}
