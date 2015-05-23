package com.tim.one.collaborator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.collaborator.AccountCollaborator;
import com.tim.one.model.BankCode;
import com.tim.one.repository.BankCodeRepository;

public class TestAccountCollaborator {

	@InjectMocks
	private AccountCollaborator accountCollaborator = new AccountCollaborator();
	
	@Mock
	private BankCodeRepository bankCodeRepository;
	@Mock
	private BankCode bankCode;
	
	private Integer bankCodeAsInteger = 40072;
	private String bankName = "bankName";
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldGetBankName() throws Exception {
		when(bankCodeRepository.findByBankCode(bankCodeAsInteger)).thenReturn(bankCode);
		when(bankCode.getName()).thenReturn(bankName);
		assertEquals(bankName, accountCollaborator.getBankName(bankCodeAsInteger));
	}
	
	@Test
	public void shouldGetEmptyString() throws Exception {
		when(bankCodeRepository.findByBankCode(bankCodeAsInteger)).thenReturn(null);
		assertEquals(StringUtils.EMPTY, accountCollaborator.getBankName(bankCodeAsInteger));
	}

}
