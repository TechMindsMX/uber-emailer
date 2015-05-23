package com.tim.one.state;


/**
 * @author josdem
 * @understands A class who knows how return response code as string
 */

public class ResponseState {

	public static String getResponse(Integer code){
		return "&response=" + code;
	}

	public static String getJsonResponse(Integer response) {
		return "{\"response\":\"" + response + "\"}";
	}
	
}
