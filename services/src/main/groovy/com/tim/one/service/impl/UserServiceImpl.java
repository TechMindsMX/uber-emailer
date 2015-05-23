package com.tim.one.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim.one.exception.NotValidParameterException;
import com.tim.one.model.User;
import com.tim.one.repository.UserRepository;
import com.tim.one.service.UserService;

/**
 * @author josdem
 * @understands A class who knows how to manage users
 * 
 */

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	public User getUserByEmail(String email) {
		if(StringUtils.isEmpty(email)){
			throw new NotValidParameterException(1);
		}
		return userRepository.findUserByEmail(email);
	}

	public User getUserById(Integer userId) {
		return userRepository.findOne(userId);
	}

	public User getUserByAccount(String account) {
		return userRepository.findUserByAccount(account);
	}

}
