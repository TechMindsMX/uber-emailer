package com.tim.one.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.service.impl.SecurityServiceImpl;

public class TestSecurityController {

	@InjectMocks
	private SecurityController securityController = new SecurityController();
	
	@Mock
	private SecurityServiceImpl securityService;

	private String key = "key";
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldGetKey() throws Exception {
		when(securityService.generateKey()).thenReturn(key);
		
		assertEquals(key, securityController.getKey());
	}

}
