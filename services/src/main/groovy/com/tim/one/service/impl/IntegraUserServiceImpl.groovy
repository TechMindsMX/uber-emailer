package com.tim.one.service.impl;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import com.tim.one.model.IntegraUser
import com.tim.one.repository.IntegraUserRepository
import com.tim.one.service.IntegraUserService
import com.tim.one.service.SecurityService
import com.tim.one.stp.service.STPClabeService
import com.tim.one.stp.state.ApplicationState

/**
 * @author josdem
 * @understands A class who knows how to manage users
 * 
 */

@Service
class IntegraUserServiceImpl implements IntegraUserService {
	
	@Autowired
	SecurityService securityService
	@Autowired
	IntegraUserRepository integraUserRepository
	@Autowired
	STPClabeService stpClabeService

	@Override
	public IntegraUser create(String uuid, String name, String email) {
		if(uuid == null){
			return new IntegraUser()
		}
		
		def user = integraUserRepository.findByIntegraUuid(uuid)
		if (!user){
			user = new IntegraUser()
			user.integraUuid = uuid
			user.name = name
			user.email= email
			user.timoneUuid = securityService.generateKey()
			integraUserRepository.save(user)
			user.stpClabe = stpClabeService.generateSTPAccount(user.id, ApplicationState.STP_INTEGRA_PREFIX)
			integraUserRepository.save(user)
		}
		
		return user
	}

	@Override
	public IntegraUser getByUuid(String uuid) {
		def user = integraUserRepository.findByTimoneUuid(uuid)
		return user == null ? new IntegraUser() : user
	}

}
