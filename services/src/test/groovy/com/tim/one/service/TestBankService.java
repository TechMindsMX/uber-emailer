package com.tim.one.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.model.TramaAccount;
import com.tim.one.repository.TramaAccountRepository;
import com.tim.one.service.impl.BankServiceImpl;

public class TestBankService {

	@InjectMocks
	private BankService bankService = new BankServiceImpl();
	
	@Mock
	private TramaAccountRepository repository;
	@Mock
	private Iterable<TramaAccount> tramaAccounts;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldGetAdministrativeAccounts() throws Exception {
		when(repository.findAll()).thenReturn(tramaAccounts);
		assertEquals(tramaAccounts, bankService.getAdministrativeAccounts());
	}

}
