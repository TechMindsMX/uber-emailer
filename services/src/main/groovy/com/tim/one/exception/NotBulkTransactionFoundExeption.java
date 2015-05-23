package com.tim.one.exception;


public class NotBulkTransactionFoundExeption extends RuntimeException {
	
	private static final long serialVersionUID = -8698794495041381675L;
	
	private Integer transactionId;

	public NotBulkTransactionFoundExeption(Integer transactionId) {
		this.transactionId = transactionId;
	}
	
	@Override
	public String getMessage() {
		return "NON BULK TRANSACTION FOUND W/ID: " + transactionId;
	}
}
