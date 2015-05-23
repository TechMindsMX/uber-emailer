package com.tim.one.exception;

public class NotTramaAccountFoundException extends RuntimeException {

	private static final long serialVersionUID = -7252233741904505136L;
	
	@Override
	public String getMessage() {
		return "NO ADMINITRATIVE TRAMA ACCOUNT WAS FOUND IN THE DATA BASE";
	}

}
