package com.tim.one.command

import javax.validation.constraints.NotNull
import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.SafeHtml

class AssignationCommand implements Command {
	
	@SafeHtml
	String name
	
	@SafeHtml
	String reference
	
	@Email
	@NotNull
	String email
	
	@Email
	String emailOptional
	
	@SafeHtml
	String note
	
}
