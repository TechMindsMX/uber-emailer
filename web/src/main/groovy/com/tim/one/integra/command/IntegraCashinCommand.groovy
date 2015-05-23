package com.tim.one.integra.command

import javax.validation.constraints.DecimalMin
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

import org.hibernate.validator.constraints.SafeHtml

import com.tim.one.command.Command

class IntegraCashinCommand implements Command {
	
	@NotNull
	@SafeHtml
	@Size(min = 32, max = 32)
  String uuid
	
	@NotNull
	@DecimalMin(value="0.01", inclusive=true)
	BigDecimal amount
	
}
