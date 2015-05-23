package com.tim.one.interceptor.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service;

import com.tim.one.interceptor.CryptoExceptionInterceptor;
import com.tim.one.service.LoggerService

@Service
class CryptoExceptionInterceptorImpl implements CryptoExceptionInterceptor {

	@Autowired
  LoggerService loggerService


	@Override
	public void intercept(RuntimeException exception) {
		def data = [:]
		data.message = exception.getMessage()
		data.cause = exception.getCause();
		loggerService.notifyRequest(data)
	}
	
}
