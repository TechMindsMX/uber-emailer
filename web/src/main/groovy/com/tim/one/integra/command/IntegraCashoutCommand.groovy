package com.tim.one.integra.command

import org.hibernate.validator.constraints.SafeHtml

import com.tim.one.command.Command;

import javax.validation.constraints.DecimalMin
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size
import javax.validation.constraints.Min

class IntegraCashoutCommand implements Command {
	
	@NotNull
	@SafeHtml
	@Size(min = 32, max = 32)
  String uuid
	
	@NotNull
	@Size(min = 18, max = 18)
	@SafeHtml
	String clabe
	
	@NotNull
	@Min(1L)
	Integer bankCode
	
	@NotNull
	@DecimalMin(value="0.01", inclusive=true)
	BigDecimal amount
	
}
