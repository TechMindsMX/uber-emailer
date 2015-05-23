package com.tim.one.exception;

public class AdvanceProviderPaidException extends RuntimeException {

	private static final long serialVersionUID = 8977481270072284474L;

	private Integer providerId;
	
	public AdvanceProviderPaidException(Integer providerId) {
		this.providerId = providerId;
	}
	
	@Override
	public String getMessage() {
		return "THE ADVANCE QUANTITY FROM PROVIDER WITH ID: " + providerId + "HAS BEEN ALREADY PAID";
	}

}
