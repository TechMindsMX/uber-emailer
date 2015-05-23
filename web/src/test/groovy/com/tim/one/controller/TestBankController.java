package com.tim.one.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.model.TramaAccount;
import com.tim.one.service.impl.BankServiceImpl;

public class TestBankController {

	@InjectMocks
	private BankController bankController = new BankController();
	
	@Mock
	private BankServiceImpl bankService;
	@Mock
	private List<TramaAccount> tramaAccounts;
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldListAdministrativeAccounts() throws Exception {
		when(bankService.getAdministrativeAccounts()).thenReturn(tramaAccounts);
		assertEquals(tramaAccounts, bankController.listAdministrativeAccounts());
	}
}
