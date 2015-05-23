package com.tim.one.common.collabotator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.common.collabotator.SecurityCollaborator;
import com.tim.one.common.util.DateUtil;

public class TestSecurityCollaborator {

	@InjectMocks
	private SecurityCollaborator securityCollaborator = new SecurityCollaborator();
	
	@Mock
	private DateUtil dateUtil;
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldKnowIsTokenValue() throws Exception {
		String value = "1000";
		when(dateUtil.createDateAsLong()).thenReturn(1500L);
		assertTrue(securityCollaborator.isTokenValue(value));
	}
	
	@Test
	public void shouldKnowIsNotTokenValue() throws Exception {
		String value = "500";
		when(dateUtil.createDateAsLong()).thenReturn(1500L);
		assertFalse(securityCollaborator.isTokenValue(value));
	}

}
