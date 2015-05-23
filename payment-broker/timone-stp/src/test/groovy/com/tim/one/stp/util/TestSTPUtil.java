package com.tim.one.stp.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.tim.one.stp.util.STPUtil;

public class TestSTPUtil {

	private STPUtil stpUtil = new STPUtil();
	
	@Test
	public void shouldGetReferenciaNumerica() throws Exception {
		Integer referenciaNumerica = stpUtil.getReferenciaNumerica();
		assertEquals(7, referenciaNumerica.toString().length());
	}

}
