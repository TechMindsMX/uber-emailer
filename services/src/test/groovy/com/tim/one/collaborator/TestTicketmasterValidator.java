package com.tim.one.collaborator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.tim.one.collaborator.TicketmasterValidator;

public class TestTicketmasterValidator {
	
private TicketmasterValidator ticketmasterValidator = new TicketmasterValidator();
	
	@Test
	public void shouldValidate() throws Exception {
		String string = "943577044333/ECCR1017/X5/LUNSUP/RL/S6/W";
		assertTrue(ticketmasterValidator.validate(string));
	}

	@Test
	public void shouldValidateWhenEmpty() throws Exception {
		String string = StringUtils.EMPTY;
		assertTrue(ticketmasterValidator.validate(string));
	}
	
	@Test
	public void shouldNotValidate() throws Exception {
		String string = "943577044333/ECCR1017/X5/LUNSUP/RLS6/W";
		assertFalse(ticketmasterValidator.validate(string));
	}

}
