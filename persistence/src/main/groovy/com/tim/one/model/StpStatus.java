package com.tim.one.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author josdem
 * @understands A class who mapped STP cash out response
 * 
 */

@Entity
@Table(name="STP_STATUS")
public class StpStatus {

	@Id
	private Integer id;
	private Integer claveRastreo;
	private Integer estado;
	private Long serverTimestamp;
	private Long createdTimestamp;
	private Long confirmationTimestamp;
	private Integer userId;
	private String uuid;
	
	@Column(columnDefinition="Decimal(38,2) default '0.00'")
	private BigDecimal amount;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getClaveRastreo() {
		return claveRastreo;
	}
	
	public void setClaveRastreo(Integer claveRastreo) {
		this.claveRastreo = claveRastreo;
	}
	
	public Integer getEstado() {
		return estado;
	}
	
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	
	public Long getServerTimestamp() {
		return serverTimestamp;
	}
	
	public void setServerTimestamp(Long serverTimestamp) {
		this.serverTimestamp = serverTimestamp;
	}
	
	public Long getCreatedTimestamp() {
		return createdTimestamp;
	}
	
	public void setCreatedTimestamp(Long createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}
	
	public Long getConfirmationTimestamp() {
		return confirmationTimestamp;
	}
	
	public void setConfirmationTimestamp(Long confirmationTimestamp) {
		this.confirmationTimestamp = confirmationTimestamp;
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
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
}
