package com.tim.one.command

import javax.validation.constraints.Min
import javax.validation.constraints.NotNull


class ProjectDeleteUnitSaleCommand implements Command {
	
	@NotNull
	@Min(1L)
  Integer projectUnitSaleId
  String token
	
}
