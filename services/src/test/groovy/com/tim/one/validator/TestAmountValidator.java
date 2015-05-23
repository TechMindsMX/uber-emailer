package com.tim.one.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

public class TestAmountValidator {

	private AmountValidator amountValidator = new AmountValidator();
	
	@Test
	public void shouldKnowIsNotValidAmountDueIsZero() throws Exception {
		BigDecimal amount = new BigDecimal(0);
		assertFalse(amountValidator.isValid(amount));
	}
	
	@Test
	public void shouldKnowIsValidAmount() throws Exception {
		BigDecimal amount = new BigDecimal("100");
		assertTrue(amountValidator.isValid(amount));
	}
	
}
