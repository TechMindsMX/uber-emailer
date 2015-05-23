package com.tim.one.exception;

public class ExpiredCodeException extends RuntimeException {

	private static final long serialVersionUID = -4244505500919002998L;

	private String code;

	public ExpiredCodeException(String code) {
		this.code = code;
	}

	@Override
	public String getMessage() {
		return "THIS CODE IS NOT LONGER VALID: " + code;
	}

}
