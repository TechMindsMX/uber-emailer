package com.tim.one.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


/**
 * @author josdem
 * @understands A class who mapped project's provider database values to the entity model
 * 
 */

@Entity
@Table(name="PROJECT_PROVIDER")
public class ProjectProvider {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer providerId;
	private Long advanceDate;
	private Long settlementDate;
	private Long advancePaidDate;
	private Long settlementPaidDate;
	private Long advanceFundingDate;
	private Long settlementFundingDate;

	@Column(columnDefinition="Decimal(38,2) default '0.00'")
	private BigDecimal advanceQuantity;
	@Column(columnDefinition="Decimal(38,2) default '0.00'")
	private BigDecimal settlementQuantity;
	
	@ManyToOne
  @JoinColumn(name = "projectId")
	@JsonProperty("projectId")
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
  @JsonIdentityReference(alwaysAsId=true)
  private Project project;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Project getProject() {
    return project;
  }
	
	public void setProject(Project project) {
    this.project = project;
  }

	public Integer getProviderId() {
		return providerId;
	}
	
	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}

	public Long getAdvanceDate() {
		return advanceDate;
	}

	public void setAdvanceDate(Long advanceDate) {
		this.advanceDate = advanceDate;
	}

	public BigDecimal getAdvanceQuantity() {
		return advanceQuantity;
	}

	public void setAdvanceQuantity(BigDecimal advanceQuantity) {
		this.advanceQuantity = advanceQuantity;
	}

	public Long getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(Long settlementDate) {
		this.settlementDate = settlementDate;
	}

	public BigDecimal getSettlementQuantity() {
		return settlementQuantity;
	}

	public void setSettlementQuantity(BigDecimal settlementQuantity) {
		this.settlementQuantity = settlementQuantity;
	}
	
	public Long getAdvancePaidDate() {
		return advancePaidDate;
	}
	
	public void setAdvancePaidDate(Long advancePaidDate) {
		this.advancePaidDate = advancePaidDate;
	}
	
	public Long getSettlementPaidDate() {
		return settlementPaidDate;
	}
	
	public void setSettlementPaidDate(Long settlementPaidDate) {
		this.settlementPaidDate = settlementPaidDate;
	}
	
	public Long getAdvanceFundingDate() {
		return advanceFundingDate;
	}
	
	public void setAdvanceFundingDate(Long advanceFundingDate) {
		this.advanceFundingDate = advanceFundingDate;
	}
	
	public Long getSettlementFundingDate() {
		return settlementFundingDate;
	}
	
	public void setSettlementFundingDate(Long settlementFundingDate) {
		this.settlementFundingDate = settlementFundingDate;
	}
	
}
