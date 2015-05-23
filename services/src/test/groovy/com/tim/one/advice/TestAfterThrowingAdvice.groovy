package com.tim.one.advice

import static org.junit.Assert.*
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

import com.stp.h2h.CryptoHandler.Exception.STPCryptoHandlerException
import com.tim.one.exception.BusinessException;
import com.tim.one.interceptor.CryptoExceptionInterceptor

class TestAfterThrowingAdvice {
	
	@InjectMocks
	AfterThrowingAdvice advice = new AfterThrowingAdvice()
	
	@Mock
	CryptoExceptionInterceptor interceptor

	RuntimeException exception = new STPCryptoHandlerException("message")
	
	@Before
	void before() {
		MockitoAnnotations.initMocks(this)
	}

	@Test(expected=BusinessException.class)
	public void shouldSendCryptoMessage() {
		advice.doRecoveryActions(exception)
		verify(interceptor).intercept(exception)
	}

}
