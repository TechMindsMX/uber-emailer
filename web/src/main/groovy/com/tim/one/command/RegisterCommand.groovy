package com.tim.one.command

import javax.validation.constraints.NotNull
import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.SafeHtml

class RegisterCommand implements Command {
	
	@Email
	@NotNull
	String email
	
	@SafeHtml
	String token
	
}
