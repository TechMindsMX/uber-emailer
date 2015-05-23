package com.tim.one.command

import javax.validation.constraints.DecimalMin
import javax.validation.constraints.Size
import javax.validation.constraints.NotNull

import org.hibernate.validator.constraints.SafeHtml


class TransferAccountCommand implements Command {
	
	@NotNull
	@SafeHtml
	@Size(min = 1, max = 50)
  String account
	
	@NotNull
	@DecimalMin(value="0.01", inclusive=true)
  BigDecimal amount
	
  String token
  String callback
	
}
