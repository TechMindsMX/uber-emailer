package com.tim.one.command

import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

import org.hibernate.validator.constraints.Range
import org.hibernate.validator.constraints.SafeHtml

class ProjectChangeStatusCommand implements Command {
	
	@NotNull
	@Min(1L)
  Integer projectId
	
	@NotNull
	@Min(0L)
  Integer userId

	@NotNull
	@Range(min=0L, max=11L)
  Integer status
	
	@Range(min=0L, max=2L)
  Integer reason
	
	@Size(min = 0, max = 1000)
	@SafeHtml
  String comment
	
  String token
  String callback
	
}

