package com.tim.one.exception;

public class RedeemedCodeException extends RuntimeException {

	private String code;

	public RedeemedCodeException(String code) {
		this.code = code;
	}

	private static final long serialVersionUID = 676036373157873150L;

	@Override
	public String getMessage() {
		return "THIS CODE HAS BEEEN REDEEMED: " + code;
	}
	
}
