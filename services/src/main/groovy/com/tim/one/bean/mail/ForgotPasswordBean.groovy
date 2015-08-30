package com.tim.one.bean.mail

import com.tim.one.bean.MessageType;

class ForgotPasswordBean implements EmailBean {
	String email
	String token
	MessageType type
}
