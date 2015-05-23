package com.tim.one.stp.collabotator;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.stp.h2h.CryptoHandler.OrdenPago;

@Component
public class OrdenPagoFormatter {

	public String getDataToSign(OrdenPago orden) {
		String retVal;
		
		NumberFormat df = DecimalFormat.getInstance();
        df.setGroupingUsed(false);
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
		
        StringBuilder data = new StringBuilder("||");

        data.append(ObjectUtils.defaultIfNull(orden.getInstitucionContraparte(), "")).append("|");
        data.append(StringUtils.trimToEmpty(orden.getEmpresa())).append("|");
        data.append(ObjectUtils.defaultIfNull(orden.getFechaOperacion(), "")).append("|");
        data.append(StringUtils.trimToEmpty(orden.getFolioOrigen())).append("|");
        data.append(StringUtils.trimToEmpty(orden.getClaveRastreo())).append("|");
        data.append(ObjectUtils.defaultIfNull(orden.getInstitucionOperante(), "")).append("|");
        data.append(df.format(orden.getMonto())).append("|");
        data.append(ObjectUtils.defaultIfNull(orden.getTipoPago(), "")).append("|");
        data.append(ObjectUtils.defaultIfNull(orden.getTipoCuentaOrdenante(), "")).append("|");
        data.append(StringUtils.trimToEmpty(orden.getNombreOrdenante())).append("|");
        data.append(StringUtils.trimToEmpty(orden.getCuentaOrdenante())).append("|");
        data.append(StringUtils.trimToEmpty(orden.getRfcCurpOrdenante())).append("|");
        data.append(ObjectUtils.defaultIfNull(orden.getTipoCuentaBeneficiario(), "")).append("|");
        data.append(StringUtils.trimToEmpty(orden.getNombreBeneficiario())).append("|");
        data.append(StringUtils.trimToEmpty(orden.getCuentaBeneficiario())).append("|");
        data.append(StringUtils.trimToEmpty(orden.getRfcCurpBeneficiario())).append("|");
        data.append(StringUtils.trimToEmpty(orden.getEmailBeneficiario())).append("|");
        data.append(ObjectUtils.defaultIfNull(orden.getTipoCuentaBeneficiario2(), "")).append("|");
        data.append(StringUtils.trimToEmpty(orden.getNombreBeneficiario2())).append("|");
        data.append(StringUtils.trimToEmpty(orden.getCuentaBeneficiario2())).append("|");
        data.append(StringUtils.trimToEmpty(orden.getRfcCurpBeneficiario2())).append("|");
        data.append(StringUtils.trimToEmpty(orden.getConceptoPago())).append("|");
        data.append(StringUtils.trimToEmpty(orden.getConceptoPago2())).append("|");
        data.append(StringUtils.trimToEmpty(orden.getClaveCatUsuario1())).append("|");
        data.append(StringUtils.trimToEmpty(orden.getClaveCatUsuario2())).append("|");
        data.append(StringUtils.trimToEmpty(orden.getClavePago())).append("|");
        data.append(StringUtils.trimToEmpty(orden.getReferenciaCobranza())).append("|");
        data.append(ObjectUtils.defaultIfNull(orden.getReferenciaNumerica(), "")).append("|");
        data.append(ObjectUtils.defaultIfNull(orden.getTipoOperacion(), "")).append("|");
        data.append(StringUtils.trimToEmpty(orden.getTopologia())).append("|");
        data.append(StringUtils.trimToEmpty(orden.getUsuario())).append("|");
        data.append(ObjectUtils.defaultIfNull(orden.getMedioEntrega(), "")).append("|");
        data.append(ObjectUtils.defaultIfNull(orden.getPrioridad(), "")).append("|");
        data.append(orden.getIva()!=null ? df.format(orden.getIva()): "").append("||");
        retVal = data.toString();

        return retVal;
	}

}
