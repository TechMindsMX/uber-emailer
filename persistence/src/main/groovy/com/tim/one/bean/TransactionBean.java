package com.tim.one.bean;

import java.math.BigDecimal;

public class TransactionBean implements Comparable<TransactionBean> {
	
	private String reference;
	private Integer bulkId;
	private String description;
	private String projectName;
	private Long timestamp;
	private BigDecimal amount;
	private BigDecimal balance;
	private StatementType type;
	
	public String getReference() {
		return reference;
	}
	
	public void setReference(String reference) {
		this.reference = reference;
	}
	
	public Integer getBulkId() {
		return bulkId;
	}
	
	public void setBulkId(Integer bulkId) {
		this.bulkId = bulkId;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public Long getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public BigDecimal getBalance() {
		return balance;
	}
	
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	public StatementType getType() {
		return type;
	}
	
	public void setType(StatementType type) {
		this.type = type;
	}

	public int compareTo(TransactionBean that) {
		return this.getTimestamp().compareTo(that.getTimestamp());
	}

}
