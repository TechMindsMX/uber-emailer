package com.tim.one.exception;

/**
 * @author josdem
 * @understands An exception class threw when an attempt to operate with an invalid status
 * 
 */

public class OperationStatusException extends RuntimeException {
	
	private static final long serialVersionUID = -4124997190109930656L;
	
	private Integer status;

	public OperationStatusException(Integer status) {
		this.status = status;
	}

	@Override
	public String getMessage() {
		return "AN ATTEPT TO OPERATE WITH STATUS: " + status + " WAS MADE BUT THIS OPERATION IS NOT ALLOWED";
	}

}
