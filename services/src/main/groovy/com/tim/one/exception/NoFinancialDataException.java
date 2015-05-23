package com.tim.one.exception;

public class NoFinancialDataException extends RuntimeException {

	private Integer projectId;

	private static final long serialVersionUID = 8219341674988195314L;
	
	public NoFinancialDataException(Integer projectId) {
		this.projectId = projectId;
	}
	
	@Override
	public String getMessage() {
		return "THE PROJECT W/ID: " + projectId + " HAS NOT FINANCIAL DATA";
	}

}
