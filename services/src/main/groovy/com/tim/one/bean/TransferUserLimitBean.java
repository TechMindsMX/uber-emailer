package com.tim.one.bean;

import java.math.BigDecimal;

public class TransferUserLimitBean {
	private Integer destinationId;
	private String account;
	private String name;
	private String email;
	private BigDecimal amount;
	private Integer id;
	
	public Integer getDestinationId() {
		return destinationId;
	}
	
	public void setDestinationId(Integer destinationId) {
		this.destinationId = destinationId;
	}
	
	public String getAccount() {
		return account;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
