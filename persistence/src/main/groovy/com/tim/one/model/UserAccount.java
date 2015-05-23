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
 * @understands A class who mapped Trama users account database values to the entity model
 * 
 */

@Entity
@Table(name="USER_ACCOUNT")
public class UserAccount{

	@Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer bankCode;
	private String clabe;
  private String stpClabe;
  
  @OneToOne
  @JoinColumn(name = "userId")
  @JsonIgnore
  private User user;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getBankCode() {
		return bankCode;
	}
	
	public void setBankCode(Integer bankCode) {
		this.bankCode = bankCode;
	}
	
	public String getClabe() {
		return clabe;
	}
	
	public void setClabe(String clabe) {
		this.clabe = clabe;
	}
	
	public String getStpClabe() {
    return stpClabe;
  }

  public void setStpClabe(String stpClabe) {
    this.stpClabe = stpClabe;
  }
  
  public User getUser() {
    return user;
  }
  
  public void setUser(User user) {
    this.user = user;
  }

}
