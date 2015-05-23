package com.tim.one.stp.collabotator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClabeValidatorCollaborator {
	
  @Autowired
  private ClabeDigitValidatorCollaborator collaborator;
					
	public boolean isValid(String clabe) {
		return Character.getNumericValue(clabe.charAt(17)) == collaborator.getVerifiedDigit(clabe);
	}

}
