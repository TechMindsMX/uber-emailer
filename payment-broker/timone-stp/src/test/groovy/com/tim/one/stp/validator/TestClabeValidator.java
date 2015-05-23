package com.tim.one.stp.validator;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.stp.collabotator.ClabeValidatorCollaborator;
import com.tim.one.stp.validator.ClabeValidator;

public class TestClabeValidator {

	@InjectMocks
	private ClabeValidator clabeValidator = new ClabeValidator();
	
	@Mock
	private ClabeValidatorCollaborator clabeValidatorCollaborator;
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldKnowIsNotValidDueToLenght() throws Exception {
		String clabe = "clabe";
		when(clabeValidatorCollaborator.isValid(clabe)).thenReturn(true);
		assertFalse(clabeValidator.isValid(clabe));
	}
	
	@Test
	public void shouldKnowIsNotValidDueToIsNotNumeric() throws Exception {
		String clabe = "02118006316474421A";
		when(clabeValidatorCollaborator.isValid(clabe)).thenReturn(true);
		assertFalse(clabeValidator.isValid(clabe));
	}
	
	@Test
	public void shouldKnowIsNotValidDueToIsNotVerified() throws Exception {
		String clabe = "021180063164744217";
		when(clabeValidatorCollaborator.isValid(clabe)).thenReturn(false);
		assertFalse(clabeValidator.isValid(clabe));
	}
	
	@Test
	public void shouldKnowIsValid() throws Exception {
		String clabe = "021180063164744216";
		when(clabeValidatorCollaborator.isValid(clabe)).thenReturn(true);
		assertTrue(clabeValidator.isValid(clabe));
	}

}
