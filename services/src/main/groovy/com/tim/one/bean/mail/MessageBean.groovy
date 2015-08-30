package com.tim.one.bean.mail

import org.hibernate.validator.constraints.SafeHtml;

import com.tim.one.bean.MessageType;

class MessageBean implements EmailBean {
	String email
	String message
	MessageType type
}
