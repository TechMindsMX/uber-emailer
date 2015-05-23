package com.tim.one.stp.service

import com.lgec.enlacefi.spei.integration.h2h.OrdenPagoWS

interface CryptoService {
  
  OrdenPagoWS assignSign(OrdenPagoWS orderPagoWS)
  
}
