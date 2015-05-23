package com.tim.one.integra.command

import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.SafeHtml

import com.tim.one.command.Command

class IntegraUserCommand implements Command {

	@NotNull
	@SafeHtml
	@Size(min = 1, max = 32)
  String uuid

	@NotNull
	@SafeHtml
	@Size(min = 1, max = 255)
	String name
	
	@Email
	@NotNull
	String email
	
}
