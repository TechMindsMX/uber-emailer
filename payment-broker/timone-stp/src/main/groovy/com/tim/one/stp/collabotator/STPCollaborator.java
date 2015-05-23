package com.tim.one.stp.collabotator;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgec.enlacefi.spei.integration.h2h.OrdenPagoWS;
import com.lgec.enlacefi.spei.integration.h2h.SpeiServices;
import com.lgec.enlacefi.spei.integration.h2h.SpeiServicesImplService;
import com.tim.one.stp.helper.STPHelper;
import com.tim.one.stp.state.ApplicationState;
import com.tim.one.stp.util.STPUtil;

@Component
public class STPCollaborator {
	
	@Autowired
	private STPHelper stpHelper;
	@Autowired
	private STPUtil stpUtil;
	@Autowired
  private Properties stpProperties;
	
	public OrdenPagoWS createOrdenPago(String name, String email, Integer bankCode, String clabe, BigDecimal amount) {
		OrdenPagoWS pago = stpHelper.createOrdenPago();
		pago.setInstitucionContraparte(bankCode);
		pago.setCuentaOrdenante(stpProperties.getProperty(ApplicationState.CUENTA_ORDENANTE));
        pago.setCuentaBeneficiario(clabe);
        pago.setTipoPago(new Integer(stpProperties.getProperty(ApplicationState.TERCERO_A_TERCERO)));
        pago.setEmailBeneficiario(email);
        pago.setInstitucionOperante(new Integer(stpProperties.getProperty(ApplicationState.INSTITUCION_OPERANTE)));
        pago.setMonto(amount);
        pago.setEmpresa(stpProperties.getProperty(ApplicationState.STP_USUARIO));
        pago.setNombreBeneficiario(name);
        pago.setTipoCuentaBeneficiario(new Integer(stpProperties.getProperty(ApplicationState.TIPO_CUENTA_BENEFICIARIO)));
        pago.setConceptoPago(stpProperties.getProperty(ApplicationState.CONCEPTO_PAGO));
        pago.setReferenciaNumerica(stpUtil.getReferenciaNumerica());
		return pago;
	}

	public SpeiServices getSpeiService() throws MalformedURLException {
		URL wsdlResource = stpHelper.createURL(stpProperties.getProperty(ApplicationState.STP_WSDL_URL));
        SpeiServicesImplService speiBuilder = stpHelper.createSpeiServicesImplService(wsdlResource);
        return speiBuilder.getSpeiServicesImplPort();
	}

}
