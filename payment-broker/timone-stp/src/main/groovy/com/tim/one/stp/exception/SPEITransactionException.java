package com.tim.one.stp.exception;

public class SPEITransactionException extends RuntimeException {

	private static final long serialVersionUID = 382833471255175734L;

	private String clabe;
	
	public SPEITransactionException(String clabe) {
		this.clabe = clabe;
	}
	
	@Override
	public String getMessage() {
		return "AN ERROR OCCURRED WHEN WE TRIED TO COMPLETE TRANSATION TO CLABE: " + clabe	;
	}

}
