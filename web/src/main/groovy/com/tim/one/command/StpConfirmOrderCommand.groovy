package com.tim.one.command

import javax.validation.constraints.Min
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range

class StpConfirmOrderCommand implements Command {
	
	@NotNull
	@Min(1L)
  Integer id
	
	@NotNull
	@Min(1L)
  Integer claveRastreo
	
	@NotNull
  @Min(1L)
	Long timestamp

	@NotNull
	@Range(min=0L, max=1L)
  Integer estado
	
  String token
	
}

