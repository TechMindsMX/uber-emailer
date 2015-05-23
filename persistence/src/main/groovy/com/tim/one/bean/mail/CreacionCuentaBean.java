package com.tim.one.bean.mail;

import com.tim.one.bean.MessageType;


/**
 * @author josdem
 * @understands A class with account creation message
 *              
 */

public class CreacionCuentaBean implements MessageBean {

	private static final long serialVersionUID = -6444472930473684901L;
	
	private String account;
	private String bankName;
	private String date;
	private String email;

  private MessageType type;
	
	public String getAccount() {
    return account;
  }
	
	public void setAccount(String account) {
    this.account = account;
  }
	
	public String getBankName() {
    return bankName;
  }
	
	public void setBankName(String bankName) {
    this.bankName = bankName;
  }
	
	public String getDate() {
    return date;
  }
	
	public void setDate(String date) {
    this.date = date;
  }
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public MessageType getType() {
    return type;
  }

  public void setType(MessageType type) {
    this.type = type;
  }
	
}
