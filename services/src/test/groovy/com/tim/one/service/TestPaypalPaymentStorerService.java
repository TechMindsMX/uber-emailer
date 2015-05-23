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

import com.tim.one.bean.TransactionType;
import com.tim.one.exception.UserNotFoundException;
import com.tim.one.model.User;
import com.tim.one.repository.UserRepository;
import com.tim.one.service.PaypalPaymentStorerService;
import com.tim.one.service.TransactionApplier;
import com.tim.one.service.TransactionLogService;
import com.tim.one.service.impl.PaypalPaymentStorerServiceImpl;

public class TestPaypalPaymentStorerService {
	
	@InjectMocks
	private PaypalPaymentStorerService paypalPaymentStorerService = new PaypalPaymentStorerServiceImpl();
	
	@Mock
	private TransactionLogService transactionLogService;
	@Mock
	private UserRepository userRepository;
	@Mock
	private TransactionApplier transactionApplier;

	private Integer userId = 1;
	private BigDecimal amount = new BigDecimal("100.00");
	private BigDecimal balance = new BigDecimal("110.00");
	private User user = new User();

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldSaveUserPayment() throws Exception {
		user.setId(userId);
		user.setBalance(balance);
		when(userRepository.findOne(userId)).thenReturn(user);
		
		assertEquals(balance, paypalPaymentStorerService.saveUserPayment(userId, amount));
		
		verify(transactionLogService).createUserLog(null, userId, userId, amount, null, TransactionType.CREDIT);
		verify(transactionApplier).addAmount(user, amount);
	}
	
	@Test(expected=UserNotFoundException.class)
	public void shouldNotSaveUserPaymentSinceUserNotExist() throws Exception {
		assertEquals(balance, paypalPaymentStorerService.saveUserPayment(userId, amount));
	}

}
