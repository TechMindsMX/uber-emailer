package com.tim.one.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tim.one.bean.KeyStatus;

@Entity
@Table(name="SESSION_KEY")
public class SessionKey {

	@Id
	private String apiKey;
	private Long timestamp;
	private KeyStatus keyStatus;

	public String getApiKey() {
		return apiKey;
	}
	
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	
	public Long getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	
	public KeyStatus getKeyStatus() {
		return keyStatus;
	}
	
	public void setKeyStatus(KeyStatus keyStatus) {
		this.keyStatus = keyStatus;
	}
	
}
