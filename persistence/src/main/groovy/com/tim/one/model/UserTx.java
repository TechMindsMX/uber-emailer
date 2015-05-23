package com.tim.one.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tim.one.bean.TransactionType;

/**
 * @author josdem
 * @understands A class who logs all user transactions to the entity model
 * 
 */

@Entity
@Table(name="USER_TX")
public class UserTx implements PersonaTx {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer projectId;
	private Integer receiverId;
	private Integer senderId;
	private String reference;
	private Long timestamp;
	private TransactionType type;

	@Column(columnDefinition="Decimal(38,2) default '0.00'")
	private BigDecimal amount;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getProjectId() {
		return projectId;
	}
	
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	
	public Integer getReceiverId() {
		return receiverId;
	}
	
	public void setReceiverId(Integer receiver) {
		this.receiverId = receiver;
	}
	
	public Integer getSenderId() {
		return senderId;
	}
	
	public void setSenderId(Integer senderId) {
		this.senderId = senderId;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public Long getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	
	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}
	
	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}
	
}
