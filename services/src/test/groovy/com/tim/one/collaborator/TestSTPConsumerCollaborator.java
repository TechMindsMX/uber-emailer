package com.tim.one.collaborator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.helper.STPConsumerHelper;
import com.tim.one.model.StpStatus;
import com.tim.one.stp.state.ApplicationState;
import com.tim.one.util.DateUtil;

public class TestSTPConsumerCollaborator {

  @InjectMocks
  private STPConsumerCollaborator stpConsumerCollaborator = new STPConsumerCollaborator();
  
  @Mock
  private DateUtil dateUtil;
  @Mock
  private Properties properties;
  @Mock
  private STPConsumerHelper stpHelper;
  @Mock
  private StpStatus stpResponse;
  
  private Integer speiId = 1;
  private Integer userId = 3;
  
  private Long timestamp = 1L;
  
  private BigDecimal amount = new BigDecimal("1000");
  
  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
  }
  
  @Test
  public void shouldCreateSTPResponse() throws Exception {
    when(stpHelper.createSTPStatus()).thenReturn(stpResponse);
    when(properties.getProperty(ApplicationState.STP_PENDIENTE)).thenReturn("2");
    when(dateUtil.createDateAsLong()).thenReturn(timestamp);
    
    StpStatus result = stpConsumerCollaborator.createOrdenStatus(speiId, userId, null, amount);
    
    assertEquals(stpResponse, result);
    verify(result).setId(speiId);
    verify(result).setEstado(2);
    verify(result).setCreatedTimestamp(timestamp);
    verify(result).setUserId(userId);
    verify(result).setAmount(amount);
  }

}
