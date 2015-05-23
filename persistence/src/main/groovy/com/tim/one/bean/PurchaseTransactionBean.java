package com.tim.one.bean;

import java.math.BigDecimal;

public class PurchaseTransactionBean {
	
	private Integer projectId;
	private String section;
	private BigDecimal unitSale;
	private Integer quantity;
	
	public Integer getProjectId() {
		return projectId;
	}
	
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	
	public String getSection() {
		return section;
	}
	
	public void setSection(String section) {
		this.section = section;
	}
	
	public BigDecimal getUnitSale() {
		return unitSale;
	}
	
	public void setUnitSale(BigDecimal unitSale) {
		this.unitSale = unitSale;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
