package com.tim.one.exception;

public class NoAccountExistException extends RuntimeException {

	private static final long serialVersionUID = 8362154370352747537L;
	
	private String account;
	
	public NoAccountExistException(String account) {
		this.account = account;
	}
	
	@Override
	public String getMessage() {
		return "THIS ACCOUNT DOES NOT EXIST: " + account;
	}

}
