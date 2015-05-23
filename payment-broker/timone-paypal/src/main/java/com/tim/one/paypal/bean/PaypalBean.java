package com.tim.one.paypal.bean;

import java.math.BigDecimal;

import com.tim.one.payment.bean.PaymentBean;

public class PaypalBean implements PaymentBean {

	private Integer userId;
	private String approvalUrl;
	private String paymentId;
	private BigDecimal amount;
	private String callback;
	private Long timestamp;
	
	public Integer getUserId() {
		return userId;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public String getApprovalUrl() {
		return approvalUrl;
	}
	
	public void setApprovalUrl(String approvalUrl) {
		this.approvalUrl = approvalUrl;
	}
	
	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public String getCallback() {
		return callback;
	}
	
	public void setCallback(String callback) {
		this.callback = callback;
	}
	
	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

}
