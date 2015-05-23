package com.tim.one.command

import javax.validation.constraints.DecimalMin
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

class ProjectProducerPaymentCommand implements Command {
	
	@NotNull
	@Min(1L)
  Integer projectId
	
	@NotNull
	@Min(1L)
  Integer producerId
	
	@NotNull
	@DecimalMin(value="0.01", inclusive=true)
  BigDecimal payment
	
  String token
  String callback
	
}
