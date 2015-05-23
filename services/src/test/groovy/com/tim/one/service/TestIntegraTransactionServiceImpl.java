package com.tim.one.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.bean.IntegraTransactionType;
import com.tim.one.exception.UserNotFoundException;
import com.tim.one.model.IntegraUser;
import com.tim.one.repository.IntegraUserRepository;
import com.tim.one.service.impl.IntegraTransactionServiceImpl;

public class TestIntegraTransactionServiceImpl {

	@InjectMocks
	private IntegraTransactionServiceImpl service = new IntegraTransactionServiceImpl();
	
	@Mock
	private IntegraUserRepository repository;
	@Mock
	private TransactionApplier transactionApplier;
	@Mock
	private IntegraUser user;
	@Mock
	private TransactionLogService logService;

	private String uuid = "uuid";
	private String transactionUuid = "uuid";
	private BigDecimal amount = new BigDecimal(100);

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test(expected=UserNotFoundException.class)
	public void shouldNotCashinDueToUserNotFound() throws Exception {
		service.cashIn(uuid, amount);
	}
	
	@Test
	public void shouldCashin() throws Exception {
		when(repository.findByTimoneUuid(uuid)).thenReturn(user);
		when(transactionApplier.hasFunds(user, amount)).thenReturn(true);
		when(logService.createIntegraUserLog(uuid, uuid, amount, null, IntegraTransactionType.BANK_CASHIN)).thenReturn(transactionUuid);
		
		String result = service.cashIn(uuid, amount);
		
		verify(transactionApplier).addAmount(user, amount);
		assertEquals(transactionUuid, result);
	}

}
