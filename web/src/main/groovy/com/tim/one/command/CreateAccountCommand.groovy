package com.tim.one.command

import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

import org.hibernate.validator.constraints.SafeHtml


class CreateAccountCommand implements Command {
	
	@NotNull
	@Min(1L)
  Integer userId
	
	@NotNull
	@Min(1L)
  Integer bankCode
	
	@NotNull
	@SafeHtml
	@Size(min = 1, max = 50)
  String clabe
	
  String token
  String callback
	
}
