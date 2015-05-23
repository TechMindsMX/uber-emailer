package com.tim.one.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestNumericUtil {

	private NumericUtil numericUtil = new NumericUtil();
	
	@Test
	public void shouldKnowIsNumber() throws Exception {
		assertFalse(numericUtil.isNumeric("josdem"));
	}
	
	@Test
	public void shouldKnowIsNotNumber() throws Exception {
		assertTrue(numericUtil.isNumeric("0123456789"));
	}

}
