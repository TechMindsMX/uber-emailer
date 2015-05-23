package com.tim.one.stp.service.impl

import java.util.Properties

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import com.tim.one.stp.collabotator.ClabeDigitValidatorCollaborator
import com.tim.one.stp.collabotator.SerialCollaborator
import com.tim.one.stp.exception.InvalidClabeException
import com.tim.one.stp.service.STPClabeService

/**
 * @author josdem
 * @understands A Class who can generate a valid STP account
 *
 */

@Service
class STPClabeServiceImpl implements STPClabeService {
  
  @Autowired
  SerialCollaborator serialHelper
  @Autowired
  ClabeDigitValidatorCollaborator clabeDigitValidatorCollaborator
  
  @Autowired
  Properties stpProperties
  
  public String generateSTPAccount(Integer userAccountId, String stpPrefix) {
    StringBuilder stpClabe = new StringBuilder()
    
    stpClabe.append(stpProperties.getProperty(stpPrefix))
    stpClabe.append(serialHelper.getSerial(userAccountId))
    Integer verifiedDigit = clabeDigitValidatorCollaborator.getVerifiedDigit(stpClabe.toString())
		stpClabe.append(verifiedDigit)
    
    return stpClabe.toString()
  }

}
