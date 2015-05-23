package com.tim.one.exception;

public class NonProjectProviderException extends RuntimeException {

	private static final long serialVersionUID = 3547471320672543003L;

	private Integer providerId;
	
	public NonProjectProviderException(Integer providerId) {
		this.providerId = providerId;
	}
	
	@Override
	public String getMessage() {
		return "NO PROVIDER EXIST WITH SUCH ID: " + providerId;
	}

}
