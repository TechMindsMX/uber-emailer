package com.tim.one.common.util;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class TestCurrencyUtil {

private CurrencyUtil currencyUtil = new CurrencyUtil();
	
	private BigDecimal amount = new BigDecimal("1000");
	
	@Test
	public void shouldFormatAsCash() throws Exception {
		String expectedFormat = "$1,000.00";
		assertEquals(expectedFormat , currencyUtil.format(amount));
	}

}
