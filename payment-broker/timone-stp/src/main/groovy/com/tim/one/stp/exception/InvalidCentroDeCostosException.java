package com.tim.one.stp.exception;

public class InvalidCentroDeCostosException extends RuntimeException {
	
	private static final long serialVersionUID = -8517658059381849551L;

	private String centroDeCostos;
	
	public InvalidCentroDeCostosException(String centroDeCostos){
		this.centroDeCostos = centroDeCostos;
	}
	
	@Override
	public String getMessage() {
		return "INVALID VALUE : " + centroDeCostos;
	}

}
