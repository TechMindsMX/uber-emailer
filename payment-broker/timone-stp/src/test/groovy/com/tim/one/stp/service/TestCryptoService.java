package com.tim.one.stp.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.lgec.enlacefi.spei.integration.h2h.OrdenPagoWS;
import com.stp.h2h.CryptoHandler.OrdenPago;
import com.stp.h2h.CryptoHandler.STPCryptoHandler;
import com.tim.one.stp.collabotator.OrdenPagoAdapter;
import com.tim.one.stp.collabotator.OrdenPagoFormatter;
import com.tim.one.stp.helper.STPHelper;
import com.tim.one.stp.service.impl.CryptoServiceImpl;
import com.tim.one.stp.state.ApplicationState;

public class TestCryptoService {

	@InjectMocks
	private CryptoService cryptoService = new CryptoServiceImpl();
	
	@Mock
	private OrdenPagoWS ordenPagoWS;
	@Mock
	private OrdenPagoAdapter orderPagoAdapter;
	@Mock
	private STPHelper stpHelper;
	@Mock
	private STPCryptoHandler stpCryptoHandler;
	@Mock
	private OrdenPago ordenPago;
	@Mock
	private OrdenPagoFormatter ordenPagoFormatter;
	@Mock
	private Properties stpProperties;

	private String sign = "sign";
	private String stpJks = "stpJks";
	private String stpPassword = "stpPassword";
	private String stpAlias = "stpAlias";
	private String dataToSign = "dataToSign";
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldAssingSign() throws Exception {
		when(stpHelper.createCryptoHandler()).thenReturn(stpCryptoHandler);
		when(orderPagoAdapter.adapt(ordenPagoWS)).thenReturn(ordenPago);
		when(ordenPagoFormatter.getDataToSign(ordenPago)).thenReturn(dataToSign);
		
		setPropertiesExpectations();
		
		when(stpCryptoHandler.sign(stpJks, stpPassword, stpAlias, dataToSign)).thenReturn(sign);
		
		OrdenPagoWS result = cryptoService.assignSign(ordenPagoWS);
		
		verify(ordenPagoWS).setFirma(sign);
		assertEquals(ordenPagoWS, result);
	}

	private void setPropertiesExpectations() {
		when(stpProperties.getProperty(ApplicationState.STP_JKS)).thenReturn(stpJks);
		when(stpProperties.getProperty(ApplicationState.STP_PASSWORD)).thenReturn(stpPassword);
		when(stpProperties.getProperty(ApplicationState.STP_ALIAS)).thenReturn(stpAlias);
	}

}
