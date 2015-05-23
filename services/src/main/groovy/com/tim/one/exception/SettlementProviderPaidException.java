package com.tim.one.exception;

public class SettlementProviderPaidException extends RuntimeException {

	private static final long serialVersionUID = 1212158826617846703L;
	
	private Integer providerId;
	
	public SettlementProviderPaidException(Integer providerId) {
		this.providerId = providerId;
	}
	
	@Override
	public String getMessage() {
		return "THE SETTLEMENT QUANTITY FROM PROVIDER WITH ID: " + providerId + "HAS BEEN ALREADY PAID";
	}

}

