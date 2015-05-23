package com.tim.one.exception;

public class NonProjectUnitSaleException extends RuntimeException {

	private static final long serialVersionUID = 6576784480862689659L;

	private String sectionCode;

	public NonProjectUnitSaleException(String sectionCode) {
		this.sectionCode = sectionCode;
	}

	@Override
	public String getMessage() {
		return "NON PROJECT UNIT SALE EXIST FOR CODE: " + sectionCode;
	}
	

}
