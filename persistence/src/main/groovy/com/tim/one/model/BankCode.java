package com.tim.one.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author josdem
 * @understands A class who mapped Trama bank codes database values to the entity model
 * @catalog
 */

@Entity
@Table(name="BANK_CODE")
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class BankCode {
	
	@Id
	private Integer bankCode;
	private String name;
	
	public Integer getBankCode() {
		return bankCode;
	}
	
	public void setBankCode(Integer bankCode) {
		this.bankCode = bankCode;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}
