package com.tim.one.stp.exception;

public class STPResponseNotExists extends RuntimeException {

	private static final long serialVersionUID = -6188431146116796634L;
	
	private Integer id;
	
	public STPResponseNotExists(Integer id) {
		this.id = id;
	}
	
	@Override
	public String getMessage() {
		return "I COULD NOT FIND ANY STPResponse w/this ID  : " + id;
	}

}
