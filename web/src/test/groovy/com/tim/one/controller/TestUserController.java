package com.tim.one.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tim.one.collaborator.UserCollaborator;
import com.tim.one.model.User;
import com.tim.one.service.impl.SecurityServiceImpl;
import com.tim.one.service.impl.UserServiceImpl;
import com.tim.one.command.UserCommand;
import com.tim.one.command.UserSaveCommand;
import com.tim.one.exception.NotValidParameterException;

public class TestUserController {

	@InjectMocks
	private UserController userController = new UserController();

	@Mock
	private UserServiceImpl userService;
	@Mock
	private User user;
	@Mock
	private User newUser;
	@Mock
	private SecurityServiceImpl securityService;
	@Mock
	private UserCollaborator userCollaborator;

	private Integer userId = 1;

	private String email = "email";
	private String token = "token";
	private String account = "account";
	private String name = "name";

  private UserCommand userCommand;
  private UserSaveCommand userSaveCommand;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);

    userSaveCommand = new UserSaveCommand();
    userSaveCommand.setEmail(email);
    userSaveCommand.setName(name);
    userSaveCommand.setToken(token);

    userCommand = new UserCommand();
    userCommand.setUserId(userId);
    userCommand.setAccount(account);
	}

	@Test
	public void shouldGetUserById() throws Exception {
		when(userService.getUserById(userId)).thenReturn(user);

		assertEquals(user, userController.getUserByEmail(userCommand));
	}

	@Test
	public void shouldCreateUser() throws Exception {
		when(securityService.isValid(token)).thenReturn(true);
		when(userCollaborator.createUser(email, name)).thenReturn(newUser);

		ResponseEntity<User> result = userController.createUser(userSaveCommand);
		
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals(newUser, result.getBody());
	}

	@Test
	public void shouldGetExistingUser() throws Exception {
		when(securityService.isValid(token)).thenReturn(true);
		when(userService.getUserByEmail(email)).thenReturn(user);

		ResponseEntity<User> result = userController.createUser(userSaveCommand);
		
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals(user, result.getBody());
	}

	@Test
	public void shouldGetNewUserWhenNoValidToken() throws Exception {
		when(securityService.isValid(token)).thenReturn(false);
		
		ResponseEntity<User> result = userController.createUser(userSaveCommand);
		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
		assertNotNull(result.getBody());
	}

	@Test
	public void shouldGetUserByAccount() throws Exception {
		when(userService.getUserByAccount(account)).thenReturn(user);

		assertEquals(user, userController.getByAccount(userCommand));
	}
	
	@Test
	public void shouldNotCreateAnUserIfEmailIsNull() throws Exception {
		userSaveCommand.setEmail(null);
		when(securityService.isValid(token)).thenReturn(true);
		when(userService.getUserByEmail(userSaveCommand.getEmail())).thenThrow(new NotValidParameterException(1));
		
		ResponseEntity<User> result = userController.createUser(userSaveCommand);
		
		verify(userCollaborator, never()).createUser(userSaveCommand.getEmail(), userSaveCommand.getName());
		assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
		assertNotNull(result.getBody());
	}

}
