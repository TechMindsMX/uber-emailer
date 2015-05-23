package com.tim.one.exception;

/**
 * @author josdem
 * @understands An exception class threw when a attempt to change status is not allowed 
 * 
 */

public class FormParamsException extends RuntimeException {

	private static final long serialVersionUID = -2313209105481086458L;

	@Override
	public String getMessage() {
		return "SECTION, UNITSALE OR CAPACITY PARAMS ELEMENTS ARE INCORRECT";
	}

}
