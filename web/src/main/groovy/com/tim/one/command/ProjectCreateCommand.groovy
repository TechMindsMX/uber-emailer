package com.tim.one.command

import javax.validation.constraints.Min
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size

import org.hibernate.validator.constraints.Range
import org.hibernate.validator.constraints.SafeHtml
import org.hibernate.validator.constraints.URL

import com.tim.one.model.ProjectType

class ProjectCreateCommand implements Command {
	
	@Min(1L)
	Integer id
	
	@Min(1L)
	@NotNull
	Integer userId
	
	@NotNull
	@SafeHtml
	@Size(min = 1, max = 255)
  String name
	
	@Size(min = 1, max = 255)
	@SafeHtml
  String inclosure
	
	@Size(min = 1, max = 255)
	@SafeHtml
  String showground
	
	@Size(min = 1, max = 255)
	@SafeHtml
  String banner
	
	@Size(min = 1, max = 255)
	@SafeHtml
  String avatar
	
	@URL
  String videoLink1
	@URL
  String videoLink2
	@URL
  String videoLink3
	@URL
  String videoLink4
	@URL
  String videoLink5
	
	@URL
  String soundCloudLink1
	@URL
  String soundCloudLink2
	@URL
  String soundCloudLink3
	@URL
  String soundCloudLink4
	@URL
  String soundCloudLink5
	
	@Size(min = 1, max = 1000)
  String description
	
	@Size(min = 1, max = 1000)
  String cast

	@URL	
  String url
	
  String projectPhotosIds
  String tags
  String photos

	@Range(min=6L, max=40L)
	Integer subcategory
	@Range(min=0L, max=11L)
  Integer status
	@Range(min=0L, max=1L)
  Integer videoPublic
	@Range(min=0L, max=1L)
  Integer audioPublic
	@Range(min=0L, max=1L)
  Integer imagePublic
	@Range(min=0L, max=1L)
  Integer infoPublic

	ProjectType type
  String token
  String callback
	
}
