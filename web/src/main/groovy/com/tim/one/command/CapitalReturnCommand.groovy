package com.tim.one.command

import javax.validation.constraints.DecimalMin
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

class CapitalReturnCommand implements Command {
	
	@NotNull
	@Min(1L)
  Integer projectId
	
	@NotNull
	@Min(1L)
  Integer providerId
	
	@NotNull
	@DecimalMin(value="0.01", inclusive=true)
  BigDecimal amount
	
  String token
  String callback
	
}
