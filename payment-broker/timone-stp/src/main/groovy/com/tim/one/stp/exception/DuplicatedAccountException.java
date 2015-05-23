package com.tim.one.stp.exception;

public class DuplicatedAccountException extends RuntimeException {

	private static final long serialVersionUID = 7938800223938643675L;
	
	private String clabe;
	
	public DuplicatedAccountException(String clabe) {
		this.clabe = clabe;
	}
	
	@Override
	public String getMessage() {
		return "DUPLICATED CLABE ACCOUNT WAS FOUND : " + clabe;
	}

}
