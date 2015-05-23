package com.tim.one.bean;

import java.math.BigDecimal;
import java.util.Date;


/**
 * @author josdem
 * @understands A class who contains ProjectProvider values with date format
 * 
 */

public class ProjectProviderBean {

	private Integer id;
	private Integer projectId;
	private Integer providerId;
	private Date advanceDate;
	private BigDecimal advanceQuantity;
	private Date settlementDate;
	private BigDecimal settlementQuantity;
	
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

	public Integer getProviderId() {
		return providerId;
	}

	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}

	public Date getAdvanceDate() {
		return advanceDate;
	}

	public void setAdvanceDate(Date advanceDate) {
		this.advanceDate = advanceDate;
	}

	public BigDecimal getAdvanceQuantity() {
		return advanceQuantity;
	}

	public void setAdvanceQuantity(BigDecimal advanceQuantity) {
		this.advanceQuantity = advanceQuantity;
	}

	public Date getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(Date settlementDate) {
		this.settlementDate = settlementDate;
	}

	public BigDecimal getSettlementQuantity() {
		return settlementQuantity;
	}

	public void setSettlementQuantity(BigDecimal settlementQuantity) {
		this.settlementQuantity = settlementQuantity;
	}
	
}
