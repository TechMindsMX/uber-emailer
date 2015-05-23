package com.tim.one.stp.collabotator;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.stp.collabotator.ClabeValidatorCollaborator;

public class TestClabeValidatorCollaborator {

  @InjectMocks
	private ClabeValidatorCollaborator collaborator = new ClabeValidatorCollaborator();
	
	@Mock
	private ClabeDigitValidatorCollaborator digitCollaborator;
	
	@Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
  }
	
	@Test
	public void shouldKnowIsValidA() throws Exception {
		String clabe = "002180032240946700";
		when(digitCollaborator.getVerifiedDigit(clabe)).thenReturn(0);
		assertTrue(collaborator.isValid(clabe));
	}
	
	@Test
	public void shouldKnowIsValidB() throws Exception {
		String clabe = "021180063164744216";
		when(digitCollaborator.getVerifiedDigit(clabe)).thenReturn(6);
		assertTrue(collaborator.isValid(clabe));
	}
	
	@Test
	public void shouldKnowIsNotValidA() throws Exception {
		String clabe = "002180032240946701";
		when(digitCollaborator.getVerifiedDigit(clabe)).thenReturn(0);
		assertFalse(collaborator.isValid(clabe));
	}
	
	@Test
	public void shouldKnowIsNotValidB() throws Exception {
		String clabe = "021180063164744217";
		when(digitCollaborator.getVerifiedDigit(clabe)).thenReturn(6);
		assertFalse(collaborator.isValid(clabe));
	}

}
