package com.tim.one.util;

import java.math.BigDecimal;
import java.text.NumberFormat;

import org.springframework.stereotype.Component;

@Component
public class CurrencyUtil {

	public String format(BigDecimal string) {
		NumberFormat defaultFormat = NumberFormat.getCurrencyInstance();
		return defaultFormat.format(string);
	}
	
}
