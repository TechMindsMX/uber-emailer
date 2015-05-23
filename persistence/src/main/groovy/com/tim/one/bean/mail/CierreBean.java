package com.tim.one.bean.mail;

import com.tim.one.bean.MessageType;


public class CierreBean implements MessageBean {

	private static final long serialVersionUID = -1799114101828510501L;
	
	private String balance;
	private String projectName;
	private String budget;
	private String email;
	private String difference;

	private MessageType type;
	
	public String getBalance() {
		return balance;
	}
	
	public void setBalance(String balance) {
		this.balance = balance;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getBudget() {
		return budget;
	}
	
	public void setBudget(String budget) {
		this.budget = budget;
	}
		
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getDifference() {
		return difference;
	}

	public void setDifference(String difference) {
		this.difference = difference;
	}
	
	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}
	
}
