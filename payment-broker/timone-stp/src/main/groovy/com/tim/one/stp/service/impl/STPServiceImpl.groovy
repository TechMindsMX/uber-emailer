package com.tim.one.stp.service.impl

import org.apache.commons.lang.builder.ToStringBuilder
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import com.lgec.enlacefi.spei.integration.h2h.OrdenPagoWS
import com.lgec.enlacefi.spei.integration.h2h.SpeiServiceResponse
import com.lgec.enlacefi.spei.integration.h2h.SpeiServices
import com.tim.one.stp.collabotator.STPCollaborator
import com.tim.one.stp.exception.SPEITransactionException
import com.tim.one.stp.service.CryptoService
import com.tim.one.stp.service.STPService

@Service
class STPServiceImpl implements STPService {

	@Autowired
	CryptoService cryptoService
	@Autowired
  STPCollaborator stpCollaborator
	
	SpeiServices speiServices
	
	Log log = LogFactory.getLog(getClass())
	
	public Integer registraOrden(String name, String email, Integer bankCode, String clabe, BigDecimal amount){
    try {
      SpeiServiceResponse spei = null
      OrdenPagoWS pago = stpCollaborator.createOrdenPago(name, email, bankCode, clabe, amount)
      OrdenPagoWS pagoWithSign = cryptoService.assignSign(pago)
      speiServices = stpCollaborator.getSpeiService()
      spei = speiServices.registraOrden(pagoWithSign)
      log.info("Pago: " + ToStringBuilder.reflectionToString(pagoWithSign))
      log.info(ToStringBuilder.reflectionToString(spei))
      
      if (spei.getDescripcionError() != null){
        log.info("Error: " + spei.getDescripcionError())
        throw new SPEITransactionException(clabe)
      }
      return spei.getId()
    } catch (MalformedURLException mfue) {
      log.error(mfue, mfue)
      throw new SPEITransactionException(clabe)
    }
	}

}
