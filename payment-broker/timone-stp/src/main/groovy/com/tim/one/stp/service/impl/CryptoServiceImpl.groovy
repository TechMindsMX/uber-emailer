package com.tim.one.stp.service.impl

import java.util.Properties

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

import com.lgec.enlacefi.spei.integration.h2h.OrdenPagoWS
import com.stp.h2h.CryptoHandler.OrdenPago
import com.stp.h2h.CryptoHandler.STPCryptoHandler
import com.tim.one.stp.collabotator.OrdenPagoAdapter
import com.tim.one.stp.collabotator.OrdenPagoFormatter
import com.tim.one.stp.helper.STPHelper
import com.tim.one.stp.service.CryptoService
import com.tim.one.stp.state.ApplicationState

@Service
class CryptoServiceImpl implements CryptoService {

	@Autowired
	OrdenPagoAdapter orderPagoAdapter
	@Autowired
	STPHelper stpHelper
	@Autowired
	OrdenPagoFormatter ordenPagoFormatter
	@Autowired
	@Qualifier("stpProperties")
	Properties stpProperties
	
	public OrdenPagoWS assignSign(OrdenPagoWS orderPagoWS) {
		String stpJks = stpProperties.getProperty(ApplicationState.STP_JKS)
		String stpPassword = stpProperties.getProperty(ApplicationState.STP_PASSWORD)
		String stpAlias = stpProperties.getProperty(ApplicationState.STP_ALIAS)

		STPCryptoHandler stpCryptoHandler = stpHelper.createCryptoHandler()
		OrdenPago ordenPago = orderPagoAdapter.adapt(orderPagoWS)
		String dataToSign = ordenPagoFormatter.getDataToSign(ordenPago)
		String sign = stpCryptoHandler.sign(stpJks, stpPassword, stpAlias, dataToSign)
		orderPagoWS.setFirma(sign)
		
		return orderPagoWS
	}

}
