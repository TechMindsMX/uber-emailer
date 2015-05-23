package com.tim.one.command

import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.SafeHtml

class UserSaveCommand implements Command {
	
	@NotNull
	@Size(min = 1, max = 255)
	@SafeHtml
	String name

	@NotNull
	@Email
	@Size(min = 1, max = 255)
  String email
	
	String token
	
}
