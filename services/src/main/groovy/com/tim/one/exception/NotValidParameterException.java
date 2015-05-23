package com.tim.one.exception;

public class NotValidParameterException extends RuntimeException {

	private Integer parameterId;

	public NotValidParameterException(Integer parameterId) {
		this.parameterId = parameterId;
	}

	private static final long serialVersionUID = 3932673167025544216L;
	
	@Override
	public String getMessage() {
		return "NOT VALID PARAMETER W/ID: " + parameterId;
	}

}
