package com.tim.one.exception;

public class TicketmasterLayoutException extends RuntimeException {

	private String line;

	public TicketmasterLayoutException(String line) {
		this.line = line;
	}

	private static final long serialVersionUID = 5892788265111557080L;
	
	@Override
	public String getMessage() {
		return "INVALID TICKETMASTER LAYOUT DUE TO THIS LINE: " + line;
	}

}
