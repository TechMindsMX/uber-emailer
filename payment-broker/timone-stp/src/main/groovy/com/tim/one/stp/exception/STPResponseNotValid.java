package com.tim.one.stp.exception;

public class STPResponseNotValid extends RuntimeException {

	private static final long serialVersionUID = -6188431146116796634L;
	
	private Integer id;
	
	public STPResponseNotValid(Integer id) {
		this.id = id;
	}
	
	@Override
	public String getMessage() {
		return "STPResponse attemped to change to no valid status w/this ID : " + id;
	}

}
