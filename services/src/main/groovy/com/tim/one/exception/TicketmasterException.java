package com.tim.one.exception;

public class TicketmasterException extends RuntimeException {

	private static final long serialVersionUID = 4679583868397014955L;

	@Override
	public String getMessage() {
		return "TICKETMASTER LAYOUT IS NOT PROVIDED AND IS REQUIRED";
	}
	
}
