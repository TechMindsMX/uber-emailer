package com.tim.one.exception;

import org.springframework.core.NestedRuntimeException;

public class BusinessException extends NestedRuntimeException {

	private static final long serialVersionUID = 1L;

	public BusinessException(String msg){
		super(msg);
	}
	
	public BusinessException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
