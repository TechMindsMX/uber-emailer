package com.tim.one.model;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class TestProject {

  private Project project = new Project();
  
  @Test
  public void shouldSetupProject() throws Exception {
    BigDecimal zero = new BigDecimal("0.00");
    assertEquals(zero , project.getTri());
    assertEquals(ProjectType.PROJECT, project.getType());
  }

}
