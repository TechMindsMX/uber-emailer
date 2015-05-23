package com.tim.one.service

import com.tim.one.model.IntegraUser

interface IntegraUserService {
  
	IntegraUser create(String uuid, String name, String email)
	IntegraUser getByUuid(String uuid)
  
}
