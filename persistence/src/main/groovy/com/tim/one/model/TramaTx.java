package com.tim.one.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tim.one.bean.EntityType;
import com.tim.one.bean.TramaAccountType;


/**
 * @author josdem
 * @understands A class who logs all trama account transactions to the entity model
 * 
 */

@Entity
@Table(name="TRAMA_TX")
public class TramaTx {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer entityId;
	private Long timestamp;
	private TramaAccountType type;
	private EntityType entityType;

	@Column(columnDefinition="decimal(38,2) default 0")
	private BigDecimal amount;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getEntityId() {
		return entityId;
	}
	
	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
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
	
	public TramaAccountType getType() {
		return type;
	}
	
	public void setType(TramaAccountType type) {
		this.type = type;
	}

	public EntityType getEntityType() {
		return entityType;
	}
	
	public void setEntityType(EntityType entityType) {
		this.entityType = entityType;
	}

}
