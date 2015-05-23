package com.tim.one.model;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author josdem
 * @understands A class who mapped Trama project financial data database values to the entity model
 * 
 */

@Entity
@Table(name="PROJECT_FINANCIAL_DATA")
public class ProjectFinancialData {
	
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer numberPublic;
	
	private String account;

	private Long fundStartDate;
	private Long fundEndDate;
	private Long productionStartDate;
	private Long premiereStartDate;
	private Long premiereEndDate;
	
	private String eventCode;

	@Column(columnDefinition="Decimal(38,2) default '0.00'")
	private BigDecimal breakeven;
	@Column(columnDefinition="Decimal(38,2) default '0.00'")
	private BigDecimal budget;
	@Column(columnDefinition="Decimal(38,2) default '0.00'")
	private BigDecimal revenuePotential;
	@Column(columnDefinition="Decimal(38,2) default '0.00'")
	private BigDecimal balance;
	@Column(columnDefinition="Decimal(38,2) default '0.00'")
	private BigDecimal tramaFee;
	
	@OneToOne
  @JoinColumn(name = "projectId")
	@JsonIgnore
  private Project project;
	
	@OneToOne(mappedBy="projectFinancialData", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	private ProjectBusinessCase projectBusinessCase;
	
	@OneToMany(mappedBy="projectFinancialData", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
  private Set<ProjectVariableCost> variableCosts;

	@OneToMany(mappedBy="projectFinancialData", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval=true)
	private Set<ProjectUnitSale> projectUnitSales;
	
	@Transient
	private BigDecimal zero = new BigDecimal("0.00");
	
	public ProjectFinancialData() {
		breakeven = zero;
		budget = zero;
		revenuePotential = zero;
		balance = zero;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public BigDecimal getBreakeven() {
		return breakeven;
	}
	
	public void setBreakeven(BigDecimal breakeven) {
		this.breakeven = breakeven;
	}
	
	public BigDecimal getBudget() {
		return budget;
	}
	
	public void setBudget(BigDecimal budget) {
		this.budget = budget;
	}
	
	public BigDecimal getRevenuePotential() {
		return revenuePotential;
	}
	
	public void setRevenuePotential(BigDecimal revenuePotential) {
		this.revenuePotential = revenuePotential;
	}
	
	public Long getProductionStartDate() {
		return productionStartDate;
	}
	
	public void setProductionStartDate(Long productionStartDate) {
		this.productionStartDate = productionStartDate;
	}
	
	public Long getPremiereStartDate() {
		return premiereStartDate;
	}
	
	public void setPremiereStartDate(Long premiereStartDate) {
		this.premiereStartDate = premiereStartDate;
	}
	
	public Long getPremiereEndDate() {
		return premiereEndDate;
	}
	
	public void setPremiereEndDate(Long premiereEndDate) {
		this.premiereEndDate = premiereEndDate;
	}
	
	public ProjectBusinessCase getProjectBusinessCase() {
		return projectBusinessCase;
	}
	
	public void setProjectBusinessCase(ProjectBusinessCase projectBusinessCase) {
		this.projectBusinessCase = projectBusinessCase;
	}
	
	public Set<ProjectUnitSale> getProjectUnitSales() {
		return projectUnitSales;
	}
	
	public void setProjectUnitSales(Set<ProjectUnitSale> projectUnitSales) {
		this.projectUnitSales = projectUnitSales;
	}
	
	public Integer getNumberPublic() {
		return numberPublic;
	}
	
	public void setNumbersPublic(Integer numbersPublic) {
		this.numberPublic = numbersPublic;
	}
	
	public BigDecimal getBalance() {
		return balance;
	}
	
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	public Long getFundStartDate() {
		return fundStartDate;
	}
	
	public void setFundStartDate(Long fundStartDate) {
		this.fundStartDate = fundStartDate;
	}
	
	public Long getFundEndDate() {
		return fundEndDate;
	}
	
	public void setFundEndDate(Long fundEndDate) {
		this.fundEndDate = fundEndDate;
	}
	
	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
	public BigDecimal getTramaFee() {
		return tramaFee;
	}

	public void setTramaFee(BigDecimal tramaFee) {
		this.tramaFee = tramaFee;
	}
	
	public Set<ProjectVariableCost> getVariableCosts() {
    return variableCosts;
  }
  
  public void setVariableCosts(Set<ProjectVariableCost> variableCosts) {
    this.variableCosts = variableCosts;
  }
  
  public Project getProject() {
		return project;
	}
  
  public void setProject(Project project) {
		this.project = project;
	}
  
  @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProjectFinancialData other = (ProjectFinancialData) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
