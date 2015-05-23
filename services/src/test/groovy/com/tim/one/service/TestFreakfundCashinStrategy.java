package com.tim.one.service;

import static org.mockito.Mockito.verify;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.bean.TransactionType;
import com.tim.one.model.User;
import com.tim.one.service.impl.FreakfundCashinStrategy;

public class TestFreakfundCashinStrategy {
	
	@InjectMocks
	private FreakfundCashinStrategy freakfundCashinStrategy = new FreakfundCashinStrategy();
	
	@Mock
	private TransactionApplier transactionApplier;
	@Mock
	private TransactionLogService transactionLogService;

	private User user = new User();
	private BigDecimal amount = new BigDecimal(100);

	private String reference = "reference";
	private String clabeOrdenante = "clabeOrdenante";
	private Integer userId = 1;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldCashIn() throws Exception {
		user.setId(userId);
		
		freakfundCashinStrategy.cashIn(user, amount, reference, clabeOrdenante);
		
		verify(transactionApplier).addAmount(user, amount);
		verify(transactionLogService).createUserLog(null, userId, userId, amount, reference, TransactionType.CASH_IN);
	}

}
