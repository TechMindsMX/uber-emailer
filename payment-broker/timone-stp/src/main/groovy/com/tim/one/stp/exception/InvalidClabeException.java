package com.tim.one.stp.exception;

public class InvalidClabeException extends RuntimeException {
	
	private static final long serialVersionUID = -8517658059381849551L;

	private String clabe;
	
	public InvalidClabeException(String clabe){
		this.clabe = clabe;
	}
	
	@Override
	public String getMessage() {
		return "INVALID CLABE VALUE : " + clabe;
	}

}
