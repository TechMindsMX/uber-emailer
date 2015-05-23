package com.tim.one.stp.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.stp.collabotator.ClabeDigitValidatorCollaborator;
import com.tim.one.stp.collabotator.SerialCollaborator;
import com.tim.one.stp.service.impl.STPClabeServiceImpl;
import com.tim.one.stp.state.ApplicationState;

public class TestSTPClabeService {
  
  @InjectMocks
  private STPClabeService STPClabeService = new STPClabeServiceImpl();
  
  @Mock
  private ClabeDigitValidatorCollaborator clabeDigitValidatorCollaborator;
  @Mock
  private SerialCollaborator serialHelper;
  @Mock
  private Properties stpProperties;
  
  private Integer userAccountId = 2;
  
  private String stpPrefix = "stpPrefix";
  
  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void shouldGenerateSTPAccount() throws Exception {
    String expectedAccount = "stpPrefix00000027";
    String expectedAccountWithOutVerifiedDigit = "stpPrefix0000002";
    when(stpProperties.getProperty(ApplicationState.STP_FREAKFUND_PREFIX)).thenReturn(stpPrefix);
    when(serialHelper.getSerial(userAccountId)).thenReturn("0000002");
    when(clabeDigitValidatorCollaborator.getVerifiedDigit(expectedAccountWithOutVerifiedDigit)).thenReturn(7);
    
    assertEquals(expectedAccount, STPClabeService.generateSTPAccount(userAccountId, ApplicationState.STP_FREAKFUND_PREFIX));
  }

}
