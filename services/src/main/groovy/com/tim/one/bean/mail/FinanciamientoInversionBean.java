package com.tim.one.bean.mail;

import com.tim.one.bean.MessageType;

public class FinanciamientoInversionBean implements MessageBean {

	private static final long serialVersionUID = -1168092737930230351L;
	
	private String projectName;
	private String amount;
	private String date;
	private String email;
	private String id;

	private MessageType type;
	
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
