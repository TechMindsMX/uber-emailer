package com.tim.one.command

import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

class DeleteLimitAmountCommand implements Command {
	
	@NotNull
	@Min(1L)
  Integer userId
	
	@NotNull
	@Min(1L)
  Integer destinationId
	
  String token
	
}
