package com.tim.one.collaborator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.tim.one.collaborator.DateFormatter;

public class TestDateFormatter {

private DateFormatter dateFormatter = new DateFormatter();
	
	@Test
	public void shouldFormatString() throws Exception {
		String string = "27-06-2013";
		String expectedResult = "1372309200000";
		Long formated = dateFormatter.format(string);
		assertEquals(expectedResult, formated.toString());
	}
	
	@Test
	public void shouldNotFormatString() throws Exception {
		assertNull(dateFormatter.format(null));
	}

}
