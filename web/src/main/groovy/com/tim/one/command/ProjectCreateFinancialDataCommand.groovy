package com.tim.one.command

import javax.validation.constraints.Min
import javax.validation.constraints.Null
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

import org.hibernate.validator.constraints.Range
import org.springframework.web.multipart.MultipartFile

class ProjectCreateFinancialDataCommand implements Command {
	
	@NotNull
	@Min(1L)
  Integer projectId
	
	@Range(min=0L, max=1L)
  Integer numberPublic

	@Size(min = 1, max = 1000)
	String eventCode
	@Size(min = 1, max = 1000)
  String section
	@Size(min = 1, max = 1000)
  String unitSale
	@Size(min = 1, max = 1000)
  String capacity
	@Size(min = 0, max = 1000)
  String codeSection

  @Size(min = 1, max = 255)
  String productionStartDate
	@Size(min = 1, max = 255)
  String premiereStartDate
	@Size(min = 1, max = 255)
  String premiereEndDate

  MultipartFile businessCase
  String token
  String callback
	
}
