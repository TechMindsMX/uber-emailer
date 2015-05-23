package com.tim.one.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tim.one.bean.TransactionType;

/**
 * @author josdem
 * @understands A class who logs all unit transactions to the entity model
 * 
 */

@Entity
@Table(name="UNIT_TX")
public class UnitTx {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer projectUnitSaleId;
	private Integer quantity;
	private Integer userId;
	private Long timestamp;
	private TransactionType type;
	
	@ManyToOne
  @JoinColumn(name = "bulkId")
	@JsonIgnore
	private BulkUnitTx bulk;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getProjectUnitSaleId() {
		return projectUnitSaleId;
	}
	
	public void setProjectUnitSaleId(Integer projectUnitSaleId) {
		this.projectUnitSaleId = projectUnitSaleId;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public BulkUnitTx getBulk() {
    return bulk;
  }
	
	public void setBulk(BulkUnitTx bulk) {
    this.bulk = bulk;
  }
	
}
