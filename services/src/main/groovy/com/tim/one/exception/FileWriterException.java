package com.tim.one.exception;

public class FileWriterException extends RuntimeException {

	private static final long serialVersionUID = -4232649017634172606L;
	
	@Override
	public String getMessage() {
		return "BUSINESS CASE IS NOT PROVIDED AND IS REQUIRED";
	}

}
