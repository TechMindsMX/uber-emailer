package com.tim.one.stp.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.lgec.enlacefi.spei.integration.h2h.OrdenPagoWS;
import com.lgec.enlacefi.spei.integration.h2h.SpeiServiceResponse;
import com.lgec.enlacefi.spei.integration.h2h.SpeiServices;
import com.tim.one.stp.collabotator.STPCollaborator;
import com.tim.one.stp.exception.SPEITransactionException;
import com.tim.one.stp.helper.STPHelper;
import com.tim.one.stp.service.impl.STPServiceImpl;
import com.tim.one.stp.validator.ClabeValidator;

public class TestSTPService {

  @InjectMocks
  private STPService stpService = new STPServiceImpl();

  @Mock
  private STPHelper stpHelper;
  @Mock
  private STPCollaborator stpCollaborator;
  @Mock
  private OrdenPagoWS ordenPago;
  @Mock
  private SpeiServiceResponse spei;
  @Mock
  private SpeiServices speiService;
  @Mock
  private ClabeValidator clabeValidator;
  @Mock
  private Properties stpProperties;
  @Mock
  private CryptoService cryptoService;
  @Mock
  private OrdenPagoWS ordenPagoWithSign;
  
  private Integer speiId = 2;
  private Integer bankCode = 3;

  private String stpError = "stpError";
  private String email = "email";
  private String name = "name";
  private String clabe = "clabe";

  private BigDecimal amount = new BigDecimal("1000");

  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
  }
  
  @Test
  public void shouldRegistraOrden() throws Exception {
    setRegistraOrdenExpectations();
    when(stpCollaborator.createOrdenPago(name, email, bankCode, clabe, amount)).thenReturn(ordenPago);
    when(spei.getId()).thenReturn(speiId);
    
    assertEquals(speiId, stpService.registraOrden(name, email, bankCode, clabe, amount));
  }
  
  @Test (expected=SPEITransactionException.class)
  public void shouldNotRegistraOrdenDueToSTPError() throws Exception {
    setRegistraOrdenExpectations();
    when(stpCollaborator.createOrdenPago(name, email, bankCode, clabe, amount)).thenReturn(ordenPago);
    when(spei.getDescripcionError()).thenReturn(stpError);
    
    stpService.registraOrden(name, email, bankCode, clabe, amount);
  }
  
  @Test (expected=SPEITransactionException.class)
  public void shouldNotRegistraOrdenDueToMalformedUrl() throws Exception {
    when(stpCollaborator.getSpeiService()).thenThrow(new MalformedURLException());
    stpService.registraOrden(name, email, bankCode, clabe, amount);
  }
  
  private void setRegistraOrdenExpectations() throws MalformedURLException {
    when(stpCollaborator.getSpeiService()).thenReturn(speiService);
    when(cryptoService.assignSign(ordenPago)).thenReturn(ordenPagoWithSign);
    when(speiService.registraOrden(ordenPagoWithSign)).thenReturn(spei);
  }
  
}
