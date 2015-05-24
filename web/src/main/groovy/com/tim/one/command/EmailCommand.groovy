package com.tim.one.command

import javax.validation.constraints.NotNull
import org.hibernate.validator.constraints.Email

class EmailCommand implements Command {
	
	@Email
	@NotNull
	String email
	
}
