package com.tim.one.collaborator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.tim.one.bean.AccountType;
import com.tim.one.collaborator.AccountCreator;

public class TestAccountCreator {

private AccountCreator accountCreator = new AccountCreator();
	
	@Test
	public void shouldCreateUserAccountIdShort() throws Exception {
		Integer id = 1;
		assertEquals("U1500000001", accountCreator.createAccount(AccountType.USER, id));
	}
	
	@Test
	public void shouldCreateAccountIdLong() throws Exception {
		Integer id = 12345678;
		assertEquals("U1512345678", accountCreator.createAccount(AccountType.USER, id));
	}
	
	@Test
	public void shouldCreateAccountVeryLong() throws Exception {
		Integer id = 123456789;
		assertEquals("U1523456789", accountCreator.createAccount(AccountType.USER, id));
	}
	
	@Test
	public void shouldCreateProjectAccount() throws Exception {
		Integer id = 1;
		assertEquals("P1500000001", accountCreator.createAccount(AccountType.PROJECT, id));
	}
	
}
