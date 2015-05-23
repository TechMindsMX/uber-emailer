package com.tim.one.common.util;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

import com.tim.one.common.util.DateUtil;

public class TestDateUtil {

	private DateUtil dateUtil = new DateUtil();
	
	@Test
	public void shouldGetDateAsLong() throws Exception {
		long now = new Date().getTime();
		long result = dateUtil.createDateAsLong();
		assertEquals(now, result, 100);
	}
}
