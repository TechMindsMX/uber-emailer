package com.tim.one.stp.collabotator;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import com.lgec.enlacefi.spei.integration.h2h.OrdenPagoWS;
import com.stp.h2h.CryptoHandler.OrdenPago;

public class OrdenPagoAdapterTest {

	private OrdenPagoAdapter adapter = new OrdenPagoAdapter();
	
	private Integer institucionContraparte = 1;
	private Integer tipoPago = 2;
	private Integer institucionOperante = 3;
	private Integer tipoCuentaBeneficiario = 4;
	private Integer referenciaNumerica = 5;
	
	private String cuentaOrdenante = "cuentaOrdenante";
	private String cuentaBeneficiario = "cuentaBeneficiario";
	private String empresa = "empresa";
	private String beneficiario = "beneficiario";
	private String conceptoPago = "conceptoPago";

	private BigDecimal monto = new BigDecimal("1000");

	@Test
	public void shouldAdaptOrdenPago() throws Exception {
		OrdenPagoWS ordenPagoWS = new OrdenPagoWS();
		ordenPagoWS.setInstitucionContraparte(institucionContraparte);
		ordenPagoWS.setCuentaOrdenante(cuentaOrdenante);
		ordenPagoWS.setCuentaBeneficiario(cuentaBeneficiario);
		ordenPagoWS.setTipoPago(tipoPago);
		ordenPagoWS.setInstitucionOperante(institucionOperante);
		ordenPagoWS.setMonto(monto );
		ordenPagoWS.setEmpresa(empresa);
		ordenPagoWS.setNombreBeneficiario(beneficiario);
		ordenPagoWS.setTipoCuentaBeneficiario(tipoCuentaBeneficiario);
		ordenPagoWS.setConceptoPago(conceptoPago);
		ordenPagoWS.setReferenciaNumerica(referenciaNumerica);
		
		OrdenPago result = adapter.adapt(ordenPagoWS);
		
		assertEquals(institucionContraparte, result.getInstitucionContraparte());
		assertEquals(cuentaOrdenante, result.getCuentaOrdenante());
		assertEquals(cuentaBeneficiario, result.getCuentaBeneficiario());
		assertEquals(tipoPago, result.getTipoPago());
		assertEquals(institucionOperante, result.getInstitucionOperante());
		assertEquals(monto, result.getMonto());
		assertEquals(empresa, result.getEmpresa());
		assertEquals(beneficiario, result.getNombreBeneficiario());
		assertEquals(tipoCuentaBeneficiario, result.getTipoCuentaBeneficiario());
		assertEquals(conceptoPago, result.getConceptoPago());
		assertEquals(referenciaNumerica, result.getReferenciaNumerica());
	}

}
