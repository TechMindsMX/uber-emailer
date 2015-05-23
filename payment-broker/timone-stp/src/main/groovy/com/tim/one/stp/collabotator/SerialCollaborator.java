package com.tim.one.stp.collabotator;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tim.one.stp.exception.InvalidClabeException;
import com.tim.one.stp.state.ApplicationState;

/**
 * @param userAccountId
 * @return
 * @throws InvalidClabeException
 * 
 * accountLength is used to measure max account length 
 */

@Component
public class SerialCollaborator {
	
	@Autowired
  private Properties stpProperties;
	
	private final static Integer DIGITO_VERIFICADOR_LENGTH = 1; 

  public String getSerial(Integer userAccountId) throws InvalidClabeException {
  	String stpPrefix = stpProperties.getProperty(ApplicationState.STP_FREAKFUND_PREFIX);
  	String cuentaOrdenante = stpProperties.getProperty(ApplicationState.CUENTA_ORDENANTE);
  	
  	Integer accountLength = cuentaOrdenante.length() - stpPrefix.length() - DIGITO_VERIFICADOR_LENGTH; 
  	
    StringBuilder account = new StringBuilder();
    if (userAccountId.toString().length() < accountLength){
      for(int i=0; i<accountLength-userAccountId.toString().length(); i++){
        account.append("0");
      }
    }
    
    if(userAccountId.toString().length() > accountLength){
      throw new InvalidClabeException("UNIQUE STP CLABE VIOLATION");
    } else {
      account.append(userAccountId);
    }
    return account.toString();
  }

}
