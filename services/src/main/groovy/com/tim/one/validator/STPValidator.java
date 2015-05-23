package com.tim.one.validator;

import org.springframework.stereotype.Component;

@Component
public class STPValidator {

	public boolean isValid(Integer estado) {
		return estado == 0 || estado == 1 ? true : false;
	}

}
