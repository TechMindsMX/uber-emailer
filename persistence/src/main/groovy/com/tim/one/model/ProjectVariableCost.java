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
 * @understands A class who mapped project's status database values to the entity model
 * 
 */

@Entity
@Table(name="PROJECT_VARIABLE_COST")
public class ProjectVariableCost {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	
	@Column(columnDefinition="Decimal(38,2) default '0.00'")
	private BigDecimal value;
	
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
	
	public ProjectFinancialData getProjectFinancialData() {
    return projectFinancialData;
  }
	
	public void setProjectFinancialData(ProjectFinancialData projectFinancialData) {
    this.projectFinancialData = projectFinancialData;
  }
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public BigDecimal getValue() {
		return value;
	}
	
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	
}
