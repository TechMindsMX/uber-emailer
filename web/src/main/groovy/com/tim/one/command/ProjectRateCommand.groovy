package com.tim.one.command

import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

import org.hibernate.validator.constraints.Range

class ProjectRateCommand implements Command {
	
	@NotNull
	@Min(1L)
  Integer projectId
	
	@NotNull
	@Min(1L)
  Integer userId
	
	@NotNull
	@Range(min=0L, max=5L)
  Integer score
	
  String token
	
}
