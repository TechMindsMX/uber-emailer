package com.tim.one.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author josdem
 * @understands A class who mapped project's business case database values to the entity model
 * 
 */

@Entity
@Table(name="PROJECT_BUSINESS_CASE")
public class ProjectBusinessCase {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	
	@OneToOne
  @JoinColumn(name = "projectId")
	@JsonIgnore
  private ProjectFinancialData projectFinancialData;
	
	public Integer getId() {
    return id;
  }
	
	public void setId(Integer id) {
    this.id = id;
  }
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public ProjectFinancialData getProjectFinancialData() {
    return projectFinancialData;
  }
	
	public void setProjectFinancialData(ProjectFinancialData projectFinancialData) {
    this.projectFinancialData = projectFinancialData;
  }
	
}
