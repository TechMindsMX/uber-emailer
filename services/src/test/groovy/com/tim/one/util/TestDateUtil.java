package com.tim.one.util;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

public class TestDateUtil {

	private DateUtil dateUtil = new DateUtil();
	
	@Test
	public void shouldGetDateAsLong() throws Exception {
		long now = new Date().getTime();
		long result = dateUtil.createDateAsLong();
		assertEquals(now, result, 100);
	}
}
