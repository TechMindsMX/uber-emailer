package com.tim.one.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.exception.NotValidParameterException;
import com.tim.one.model.ProjectUnitSale;
import com.tim.one.model.UnitTx;
import com.tim.one.model.User;
import com.tim.one.repository.UserRepository;
import com.tim.one.service.impl.UserServiceImpl;

public class TestUserService {

	@InjectMocks
	private UserServiceImpl userService = new UserServiceImpl();

	@Mock
	private UserRepository userRepository;
	@Mock
	private User user;
	@Mock
	private UnitTx unitTx;
	@Mock
	private ProjectUnitSale projectUnitSale;
	@Mock
	private UnitTx otherUnitTx;
	@Mock
	private ProjectUnitSale otherProjectUnitSale;

	private Integer userId = 1;
	private String userEmail = "userEmail";
	private String account = "account";

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldGetUserById() throws Exception {
		when(userRepository.findOne(userId)).thenReturn(user);
		assertEquals(user, userService.getUserById(userId));
	}
	
	@Test
	public void shouldGetUserByEmail() throws Exception {
		when(userRepository.findUserByEmail(userEmail)).thenReturn(user);
		assertEquals(user, userService.getUserByEmail(userEmail));
	}
	
	@Test
	public void shouldGetNewUser() throws Exception {
		assertNull(userService.getUserByEmail(userEmail));
	}
	
	@Test
	public void shouldGetUserByAccount() throws Exception {
		when(userRepository.findUserByAccount(account)).thenReturn(user);
		assertEquals(user, userService.getUserByAccount(account));
	}
	
	@Test(expected=NotValidParameterException.class)
	public void shouldNotFindUserIfEmailIsNull() throws Exception {
		userService.getUserByEmail(null);
	}

}
