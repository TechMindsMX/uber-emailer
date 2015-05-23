package com.tim.one.command

import javax.validation.constraints.Min
import javax.validation.constraints.Size

import org.hibernate.validator.constraints.SafeHtml

class ProjectMessageCommand {
	
	@Min(1L)
  Integer projectId
	
	@Min(1L)
  Integer userId
	
	@SafeHtml
	@Size(min = 1, max = 1000)
  String comment
	
  String token
  String callback
	
}
