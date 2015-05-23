package com.tim.one.command

import javax.validation.constraints.DecimalMin
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

import org.hibernate.validator.constraints.SafeHtml


class StpCashInCommand implements Command {
	
	@NotNull
	@Size(min = 18, max = 18)
  String clabeOrdenante
	
	@NotNull
	@Size(min = 18, max = 18)
	String clabeBeneficiario
	
	@NotNull
	@DecimalMin(value="0.01", inclusive=true)
	BigDecimal amount
	
	@NotNull
	@SafeHtml
	@Size(min = 1, max = 50)
  String reference
	
  String token
	
}

