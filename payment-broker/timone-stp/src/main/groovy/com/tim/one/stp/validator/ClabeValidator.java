package com.tim.one.stp.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tim.one.stp.collabotator.ClabeValidatorCollaborator;

@Component
public class ClabeValidator {

	@Autowired
	private ClabeValidatorCollaborator clabeValidatorCollaborator;

	public boolean isValid(String clabe) {
		String regex = "\\d+";
		if (clabe.length() != 18){
			return false;
		}
		if (!clabe.matches(regex)){
			return false;
		}
		if (!clabeValidatorCollaborator.isValid(clabe)){
			return false;
		}
		return true;
	}

}
