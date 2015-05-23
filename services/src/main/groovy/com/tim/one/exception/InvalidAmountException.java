package com.tim.one.exception;

import java.math.BigDecimal;

public class InvalidAmountException extends RuntimeException {

  private static final long serialVersionUID = 5793768974257362870L;
  
  private BigDecimal amount;
  
  public InvalidAmountException(BigDecimal amount) {
    this.amount = amount;
  }

  @Override
  public String getMessage() {
    return "INVALID AMOUNT PROVIDED: " + amount;
  }

}
