package com.tim.one.advice

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

import com.stp.h2h.CryptoHandler.Exception.STPCryptoHandlerException
import com.tim.one.exception.BusinessException
import com.tim.one.interceptor.CryptoExceptionInterceptor
import org.springframework.beans.factory.annotation.Autowired

@Component
@Aspect
public class AfterThrowingAdvice {

	private Log log = LogFactory.getLog(getClass())
	
	@Autowired
	CryptoExceptionInterceptor interceptor

	@AfterThrowing(pointcut = "execution(* com.tim.one.service..**.*(..))", throwing = "ex")
	public void doRecoveryActions(RuntimeException ex) {
		log.info("Wrapping exception " + ex.getMessage())
		interceptor.intercept(ex)
		throw new BusinessException(ex.getMessage(), ex)
	}
}
