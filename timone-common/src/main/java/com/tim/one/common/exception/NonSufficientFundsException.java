package com.tim.one.common.exception;

public class NonSufficientFundsException extends RuntimeException {

	private static final long serialVersionUID = -799761516615318031L;
	
	private Integer id;

	public NonSufficientFundsException(Integer id) {
		this.id = id;
	}
	
	@Override
	public String getMessage() {
		return "NON SUFFICIENT FUNDS FOR ID: " + id;
	}
}
