package com.tim.one.stp.collabotator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.lgec.enlacefi.spei.integration.h2h.OrdenPagoWS;
import com.lgec.enlacefi.spei.integration.h2h.SpeiServices;
import com.lgec.enlacefi.spei.integration.h2h.SpeiServicesImplService;
import com.tim.one.stp.helper.STPHelper;
import com.tim.one.stp.state.ApplicationState;
import com.tim.one.stp.util.STPUtil;

public class TestSTPCollaborator {

	@InjectMocks
	private STPCollaborator stpCollaborator = new STPCollaborator();
	
	@Mock
	private STPHelper stpHelper;
	@Mock
	private OrdenPagoWS pago;
	@Mock
	private SpeiServicesImplService speiBuilder;
	@Mock
	private SpeiServices speiService;
	@Mock
	private STPUtil stpUtil;
	@Mock
	private Properties stpProperties;
	
	private Integer bankCode = 2;
	private Integer referenciaNumerica = 1234567;
	
	private String clabe = "account";
	private String userName = "userName";
	private String email = "email";
	private String stpWsdlUrl = "stpWsdlUrl";
	private String conceptoPago = "conceptoPago";
	private String terceroATercero = "1";
	private String institucionOperante = "3";
	private String tipoCuentaBeneficiario = "40";
	
	private URL wsdlResource;

	private BigDecimal amount = new BigDecimal("1000");

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldCreateOrdenPago() throws Exception {
		when(stpProperties.getProperty(ApplicationState.TERCERO_A_TERCERO)).thenReturn(terceroATercero);
		when(stpProperties.getProperty(ApplicationState.INSTITUCION_OPERANTE)).thenReturn(institucionOperante);
		when(stpProperties.getProperty(ApplicationState.TIPO_CUENTA_BENEFICIARIO)).thenReturn(tipoCuentaBeneficiario);
		when(stpProperties.getProperty(ApplicationState.CONCEPTO_PAGO)).thenReturn(conceptoPago);
		
		when(stpHelper.createOrdenPago()).thenReturn(pago);
		when(stpUtil.getReferenciaNumerica()).thenReturn(referenciaNumerica);
		
		stpCollaborator.createOrdenPago(userName, email, bankCode, clabe, amount);
		
		verify(pago).setInstitucionContraparte(bankCode);
		verify(pago).setCuentaBeneficiario(clabe);
		verify(pago).setTipoPago(new Integer(terceroATercero));
		verify(pago).setEmailBeneficiario(email);
		verify(pago).setInstitucionOperante(new Integer(institucionOperante));
		verify(pago).setMonto(amount);
		verify(pago).setNombreBeneficiario(userName);
		verify(pago).setReferenciaNumerica(referenciaNumerica);
		verify(pago).setTipoCuentaBeneficiario(new Integer(tipoCuentaBeneficiario));
		verify(pago).setConceptoPago(conceptoPago);
	}
	
	@Test
	public void shouldGetSpeiService() throws Exception {
		when(stpProperties.getProperty(ApplicationState.STP_WSDL_URL)).thenReturn(stpWsdlUrl);
		
		when(stpHelper.createURL(stpWsdlUrl)).thenReturn(wsdlResource);
		when(stpHelper.createSpeiServicesImplService(wsdlResource)).thenReturn(speiBuilder);
		when(speiBuilder.getSpeiServicesImplPort()).thenReturn(speiService);
		
		assertEquals(speiService, stpCollaborator.getSpeiService());
	}
	
}
