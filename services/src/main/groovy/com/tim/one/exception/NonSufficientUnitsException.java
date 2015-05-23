package com.tim.one.exception;

public class NonSufficientUnitsException extends RuntimeException {

	private static final long serialVersionUID = 6073549087998744589L;
	
	@Override
	public String getMessage() {
		return "NON SUFFICIENT UNITS TO COMPLETE PURCHASE";
	}

}
