package com.tim.one.stp.collabotator;

import org.springframework.stereotype.Component;

import com.lgec.enlacefi.spei.integration.h2h.OrdenPagoWS;
import com.stp.h2h.CryptoHandler.OrdenPago;

@Component
public class OrdenPagoAdapter {

	public OrdenPago adapt(OrdenPagoWS ordenPagoWS) {
		OrdenPago ordenPago = new OrdenPago();
		ordenPago.setEmpresa(ordenPagoWS.getEmpresa());
		ordenPago.setConceptoPago(ordenPagoWS.getConceptoPago());
		ordenPago.setCuentaBeneficiario(ordenPagoWS.getCuentaBeneficiario());
		ordenPago.setCuentaOrdenante(ordenPagoWS.getCuentaOrdenante());
		ordenPago.setReferenciaNumerica(ordenPagoWS.getReferenciaNumerica());
		ordenPago.setMonto(ordenPagoWS.getMonto());
		ordenPago.setTipoCuentaBeneficiario(ordenPagoWS.getTipoCuentaBeneficiario());
		ordenPago.setTipoPago(ordenPagoWS.getTipoPago());
		ordenPago.setInstitucionContraparte(ordenPagoWS.getInstitucionContraparte());
		ordenPago.setNombreBeneficiario(ordenPagoWS.getNombreBeneficiario());
		ordenPago.setEmailBeneficiario(ordenPagoWS.getEmailBeneficiario());
		ordenPago.setInstitucionOperante(ordenPagoWS.getInstitucionOperante());
		return ordenPago;
	}

}
