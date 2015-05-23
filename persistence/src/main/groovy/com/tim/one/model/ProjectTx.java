package com.tim.one.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tim.one.bean.ProjectTxType;

/**
 * @author josdem
 * @understands A class who logs all project/producer transactions to the entity model
 * 
 */

@Entity
@Table(name="PROJECT_TX")
public class ProjectTx {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer projectId;
	private Integer userId;
	private Long timestamp;
	private ProjectTxType type;

	@Column(columnDefinition="decimal(38,2) default 0")
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
	
	public Integer getUserId() {
		return userId;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
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
	
	public ProjectTxType getType() {
		return type;
	}
	
	public void setType(ProjectTxType type) {
		this.type = type;
	}
	
}
