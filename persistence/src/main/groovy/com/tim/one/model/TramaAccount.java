package com.tim.one.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tim.one.bean.AdminAccountType;

/**
 * @author josdem
 * @understands A class who mapped Trama account database values to the entity model
 * 
 */

@Entity
@Table(name="TRAMA_ACCOUNT")
public class TramaAccount {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String account;
	private String clabe;
	private AdminAccountType type;
	
	@Column(columnDefinition="Decimal(38,2) default '0.00'")
	private BigDecimal balance;

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getAccount() {
		return account;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}
	
	public String getClabe() {
		return clabe;
	}
	
	public void setClabe(String clabe) {
		this.clabe = clabe;
	}
	
	public BigDecimal getBalance() {
		return balance;
	}
	
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	public AdminAccountType getType() {
		return type;
	}
	
	public void setType(AdminAccountType type) {
		this.type = type;
	}

}
