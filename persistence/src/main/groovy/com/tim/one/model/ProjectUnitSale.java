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

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author josdem
 * @understands A class who mapped project's unitsale database values to the entity model
 * 
 */

@Entity
@Table(name="PROJECT_UNITSALE")
public class ProjectUnitSale {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String section;
	private Integer unit;
	private String codeSection;
	
	@Column(columnDefinition="Decimal(38,2) default '0.00'")
	private BigDecimal unitSale;

	@ManyToOne
  @JoinColumn(name = "projectId")
	@JsonIgnore
  private ProjectFinancialData projectFinancialData;

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
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
	
	public Integer getUnit() {
		return unit;
	}
	
	public void setUnit(Integer unit) {
		this.unit = unit;
	}
	
	public ProjectFinancialData getProjectFinancialData() {
    return projectFinancialData;
  }
	
	public void setProjectFinancialData(ProjectFinancialData projectFinancialData) {
    this.projectFinancialData = projectFinancialData;
  }

	public String getCodeSection() {
		return codeSection;
	}

	public void setCodeSection(String codeSection) {
		this.codeSection = codeSection;
	}
	
}
