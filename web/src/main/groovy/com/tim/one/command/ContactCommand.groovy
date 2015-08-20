package com.tim.one.command

import javax.validation.constraints.NotNull

import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.SafeHtml

class ContactCommand implements Command {
	
	@Email
	@NotNull
	String email
	
	@SafeHtml
	String name
	
	@Email
	String emailOptional
	
	@SafeHtml
	String phone
	
	@SafeHtml
	String subject
	
	@SafeHtml
	String message
	
}
