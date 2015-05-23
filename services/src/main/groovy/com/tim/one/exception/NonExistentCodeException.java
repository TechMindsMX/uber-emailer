package com.tim.one.exception;

public class NonExistentCodeException extends RuntimeException {

	private static final long serialVersionUID = -805195335370384777L;

	private String code;

	public NonExistentCodeException(String code) {
		this.code = code;
	}

	
	@Override
	public String getMessage() {
		return "NO CODE WAS FOUND WITH ID: " + code;
	}

}
