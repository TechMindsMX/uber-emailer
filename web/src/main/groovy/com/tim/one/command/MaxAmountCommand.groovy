package com.tim.one.command

import javax.validation.constraints.DecimalMin
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

class MaxAmountCommand implements Command {
	
	@NotNull
	@Min(1L)
  Integer userId
	
	@NotNull
	@Min(1L)
  Integer destinationId
	
	@NotNull
	@DecimalMin(value="0.01", inclusive=true)
  BigDecimal amount
	
  String token
	
}
