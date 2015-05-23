package com.tim.one.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.model.User;
import com.tim.one.repository.UserRepository;
import com.tim.one.service.impl.TransactionApplierImpl;
import com.tim.one.validator.AmountValidator;

public class TestTransactionApplier {

	@InjectMocks
	private TransactionApplier transactionApplier = new TransactionApplierImpl();
	
	@Mock
	private User user;
	@Mock
	private UserRepository entityDao;
	@Mock
	private AmountValidator amountValidator;

	private Integer userId = 1;
	private BigDecimal userBalance = new BigDecimal("1500");
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(entityDao.findOne(userId)).thenReturn(user);
		when(user.getBalance()).thenReturn(userBalance);
	}
	
	@Test
	public void shouldKnowHasFunds() throws Exception {
		BigDecimal amount = new BigDecimal("500");
		assertTrue(transactionApplier.hasFunds(user, amount));
	}
	
	@Test
	public void shouldKnowNotHasFunds() throws Exception {
		BigDecimal amount = new BigDecimal("2000");
		assertFalse(transactionApplier.hasFunds(user, amount));
	}
	
	@Test
	public void shouldSubstractAmount() throws Exception {
		BigDecimal amount = new BigDecimal("500");
		when(amountValidator.isValid(amount)).thenReturn(true);
		
		transactionApplier.substractAmount(user, amount);
		
		verify(user).setBalance(userBalance.subtract(amount));
		verify(entityDao).save(user);
	}
	
	@Test
	public void shouldAddAmount() throws Exception {
		BigDecimal amount = new BigDecimal("500");
		when(amountValidator.isValid(amount)).thenReturn(true);
		
		transactionApplier.addAmount(user, amount);
		
		verify(user).setBalance(userBalance.add(amount));
		verify(entityDao).save(user);
	}

}
