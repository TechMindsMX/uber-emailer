package com.tim.one.validator

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.stereotype.Service

import com.tim.one.command.Command
import javax.validation.ConstraintViolation
import javax.validation.Validation
import javax.validation.Validator
import javax.validation.constraints.Size

@Service
class CommandValidator {
	private Log log = LogFactory.getLog(getClass())
	
	public Boolean isValid(Command command) {
	  Validator validator = Validation.buildDefaultValidatorFactory().getValidator()
	  Set<ConstraintViolation<Command>> constraintViolations = validator.validate(command)
	  log.info("violations:" + constraintViolations.size())
	  if(!constraintViolations.isEmpty()){
	    log.info("field:" + constraintViolations.iterator().next().getPropertyPath())
	    log.info("message:" + constraintViolations.iterator().next().getMessage())
	  }
	  return constraintViolations.isEmpty()
	}
}
