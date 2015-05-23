package com.tim.one.common.exception;

public class SPEITransactionException extends RuntimeException {

	private static final long serialVersionUID = 382833471255175734L;

	private Integer userId;
	
	public SPEITransactionException(Integer userId) {
		this.userId = userId;
	}
	
	@Override
	public String getMessage() {
		return "AN ERROR OCCURRED WHEN WE TRIED TO COMPLETE TRANSATION TO USER: " + userId	;
	}

}
