package com.tim.one.bean.mail;

import com.tim.one.bean.MessageType;

/**
 * @author josdem
 * @understands A class with one single message
 *              
 */

public class SimpleBean implements MessageBean {

	private static final long serialVersionUID = -6444472930473684901L;
	
	private String message;
	private String email;

	private MessageType type;
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

}
