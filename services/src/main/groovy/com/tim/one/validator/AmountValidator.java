package com.tim.one.validator;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class AmountValidator {

	public Boolean isValid(BigDecimal amount) {
		return amount.compareTo(new BigDecimal(0)) > 0 ? true : false;
	}

}
