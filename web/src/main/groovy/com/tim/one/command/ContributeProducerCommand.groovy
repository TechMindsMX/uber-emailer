package com.tim.one.command

import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

class ContributeProducerCommand implements Command {
	
	@NotNull
	@Min(1L)
  Integer projectId
	
  String token
  String callback
	
}
