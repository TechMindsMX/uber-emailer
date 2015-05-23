package com.tim.one.stp.collabotator;

import java.math.BigDecimal;

import com.stp.h2h.CryptoHandler.STPCryptoHandler;

public class CrytoMain {
	
	public static void main( String[] args )
    {
        com.stp.h2h.CryptoHandler.OrdenPago op = new com.stp.h2h.CryptoHandler.OrdenPago();
        op.setEmpresa("pruebas");
        op.setClaveRastreo("IACH0OEE80002");
        op.setConceptoPago("SWI SPEI Payment Ñ á é");
        op.setCuentaBeneficiario("110180077000000018");
        op.setCuentaOrdenante("846180000050000011");
        op.setReferenciaNumerica(2);
        op.setMonto(new BigDecimal(4600000.82));
        op.setTipoCuentaBeneficiario(40);
        op.setTipoPago(1);
        op.setInstitucionContraparte(90633);
        op.setNombreBeneficiario("alfredo");
        op.setInstitucionOperante(846);
        op.setIva(16d);


        STPCryptoHandler stpCH = new STPCryptoHandler();
        String retVal = stpCH.sign("src/main/resources/prueba.jks", "12345678", "prueba", op);
        System.out.println(retVal);
    }

}
