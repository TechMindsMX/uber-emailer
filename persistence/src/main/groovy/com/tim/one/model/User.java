package com.tim.one.model;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * @author josdem
 * @understands A class who mapped Trama users database values to the entity model
 * 
 */

@Entity
@Table(name="USER")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class User implements Persona {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String email;
	private String name;
	private String account;
	
	@Column(columnDefinition="Decimal(38,2) default '0.00'")
  private BigDecimal balance;
	
	@OneToOne(mappedBy="user", cascade={CascadeType.PERSIST, CascadeType.REMOVE})
  private UserAccount userAccount;
	
	@OneToMany(mappedBy="user", cascade={CascadeType.PERSIST, CascadeType.REMOVE})
  @JsonBackReference
	private Set<Project> projects;
	
	public User() {
		balance = new BigDecimal("0.00");
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public BigDecimal getBalance() {
		return balance;
	}
	
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
	public UserAccount getUserAccount() {
    return userAccount;
  }
	
	public void setUserAccount(UserAccount userAccount) {
    this.userAccount = userAccount;
  }
	
	public Set<Project> getProjects() {
		return projects;
	}
	
	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

}
