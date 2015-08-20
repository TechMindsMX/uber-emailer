package com.tim.one.bean.mail

import org.hibernate.validator.constraints.SafeHtml;

import com.tim.one.bean.MessageType;

class ContactBean implements MessageBean {
	String email
	String name
	String emailOptional
	String phone
	String subject
	String message
	MessageType type
}
