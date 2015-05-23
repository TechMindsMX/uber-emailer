package com.tim.one.command

import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size


class ProjectProviderCommand implements Command {

	@NotNull
	@Min(1L)
	Integer projectId
	
	@NotNull
	@Size(min = 20, max = 1000)
  String projectProvider
	
  String token
  String callback
	
}
