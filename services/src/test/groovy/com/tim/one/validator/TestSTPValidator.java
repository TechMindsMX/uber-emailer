package com.tim.one.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestSTPValidator {

	private STPValidator stpValidator = new STPValidator();
	
	@Test
	public void shouldKnowIsNotValidStatus() throws Exception {
		Integer estado = -1;
		assertFalse(stpValidator.isValid(estado));
	}
	
	@Test
	public void shouldKnowIsValidStatusZero() throws Exception {
		Integer estado = 0;
		assertTrue(stpValidator.isValid(estado));
	}
	
	@Test
	public void shouldKnowIsValidStatusOne() throws Exception {
		Integer estado = 1;
		assertTrue(stpValidator.isValid(estado));
	}
	
	@Test
	public void shouldKnowIsNotValidStatusTwo() throws Exception {
		Integer estado = 2;
		assertFalse(stpValidator.isValid(estado));
	}

}
