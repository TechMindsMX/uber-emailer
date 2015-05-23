package com.tim.one.stp.collabotator;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestClabeDigitValidatorCollaborator {
  
  private ClabeDigitValidatorCollaborator collaborator = new ClabeDigitValidatorCollaborator();

  @Test
  public void shouldKnowClabeDigitA() throws Exception {
    Integer expectedDigit = 0;
    String clabe = "002180032240946700";
    assertEquals(expectedDigit, collaborator.getVerifiedDigit(clabe));
  }
  
  @Test
  public void shouldKnowClabeDigitB() throws Exception {
    Integer expectedDigit = 6;
    String clabe = "021180063164744216";
    assertEquals(expectedDigit, collaborator.getVerifiedDigit(clabe));
  }
  
  @Test
  public void shouldKnowClabeDigitC() throws Exception {
    Integer expectedDigit = 0;
    String clabe = "64618011190110001";
    assertEquals(expectedDigit, collaborator.getVerifiedDigit(clabe));
  }

  @Test
  public void shouldKnowClabeDigitD() throws Exception {
    Integer expectedDigit = 7;
    String clabe = "64618011190110000";
    assertEquals(expectedDigit, collaborator.getVerifiedDigit(clabe));
  }
  
  @Test
  public void shouldKnowClabeDigitE() throws Exception {
    Integer expectedDigit = 3;
    String clabe = "64618011190110002";
    assertEquals(expectedDigit, collaborator.getVerifiedDigit(clabe));
  }

}

