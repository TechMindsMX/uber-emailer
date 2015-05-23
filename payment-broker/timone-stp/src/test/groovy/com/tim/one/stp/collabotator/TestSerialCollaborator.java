package com.tim.one.stp.collabotator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.stp.exception.InvalidClabeException;
import com.tim.one.stp.state.ApplicationState;

public class TestSerialCollaborator {

	@InjectMocks
  private SerialCollaborator serialCollaborator = new SerialCollaborator();
	
	@Mock
	private Properties stpProperties;

	private String cuentaOrdenante = "646180111901100010"; 
	private String stpPrefix = "6461801119011";
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(stpProperties.getProperty(ApplicationState.STP_FREAKFUND_PREFIX)).thenReturn(stpPrefix);
		when(stpProperties.getProperty(ApplicationState.CUENTA_ORDENANTE)).thenReturn(cuentaOrdenante);
	}
  
  @Test
  public void shouldGetValidSerial() throws Exception {
    String expectedSerial = "0002";
    assertEquals(expectedSerial, serialCollaborator.getSerial(2));
  }
  
  @Test
  public void shouldGetValidSerialInFullStack() throws Exception {
    String expectedSerial = "1234";
    assertEquals(expectedSerial, serialCollaborator.getSerial(1234));
  }
  
  @Test (expected=InvalidClabeException.class)
  public void shouldNotGetValidSerialDueToExceedLenght() throws Exception {
    serialCollaborator.getSerial(12345);
  }

}
