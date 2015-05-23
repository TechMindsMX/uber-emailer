package com.tim.one.stp.collabotator;

import org.springframework.stereotype.Component;

@Component
public class ClabeDigitValidatorCollaborator {
  
  int[] ponderacion = new int[] {3,7,1,3,7,1,3,7,1,3,7,1,3,7,1,3,7};

  public Integer getVerifiedDigit(String clabe){
    int resultado = 0;
    int modulo = 0;
    int acomulador = 0;
    for(int i=0; i<17; i++){
      resultado = ponderacion[i] * Character.getNumericValue(clabe.charAt(i));  
      modulo = resultado % 10;
      acomulador += modulo;
    }
    int a = acomulador % 10;
    int b = 10 - a;
    int digito = b % 10;
    return digito;
  }
  
}
