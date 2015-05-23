package com.tim.one.command

import javax.validation.constraints.Min
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range

class ProviderPartnershipCommand implements Command {
	
	@NotNull
	@Min(1L)
  Integer providerId
	
	@NotNull
	@Min(1L)
  Integer projectId

	@NotNull
	@Range(min=0L, max=2L)
  Integer type
	
  String token
  String callback
	
}
