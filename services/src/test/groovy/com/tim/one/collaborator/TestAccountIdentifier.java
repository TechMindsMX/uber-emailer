package com.tim.one.collaborator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.tim.one.bean.AccountType;
import com.tim.one.collaborator.AccountIdentifier;

public class TestAccountIdentifier {

private AccountIdentifier accountIdentifier = new AccountIdentifier();
	
	@Test
	public void shouldGetUserAccount() throws Exception {
		String account = "U1400000001";
		assertEquals(AccountType.USER, accountIdentifier.getTypeByAccount(account));
	}
	
	@Test
	public void shouldGetProjectAccount() throws Exception {
		String account = "P1400000001";
		assertEquals(AccountType.PROJECT, accountIdentifier.getTypeByAccount(account));
	}

}
