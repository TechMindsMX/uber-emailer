package com.tim.one.emailer.helper

import static org.junit.Assert.assertNotNull
import static org.mockito.Mockito.when

import org.junit.Test

public class TestEmailHelper {

	private EmailHelper helper = new EmailHelper()

	@Test
	public void shouldCreateABodyPart() throws Exception {
		assertNotNull(helper.createMessageBodyPart())
	}

}
