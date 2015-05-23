package com.tim.one.stp.helper;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.stereotype.Component;

import com.lgec.enlacefi.spei.integration.h2h.OrdenPagoWS;
import com.lgec.enlacefi.spei.integration.h2h.SpeiServicesImplService;
import com.stp.h2h.CryptoHandler.STPCryptoHandler;

@Component
public class STPHelper {
	
	public URL createURL(String stpWsdlUrl) throws MalformedURLException {
		return new URL(stpWsdlUrl);
	}

	public SpeiServicesImplService createSpeiServicesImplService(URL wsdlResource) {
		return new SpeiServicesImplService(wsdlResource);
	}

	public OrdenPagoWS createOrdenPago() {
		return new OrdenPagoWS();
	}

	public STPCryptoHandler createCryptoHandler() {
		return new STPCryptoHandler();
	}

}
