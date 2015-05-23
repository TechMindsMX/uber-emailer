package com.tim.one.status

enum TimoneErrorStatus {
  TOKEN_EXPIRED(1, "Token session expired"),
  NON_SUFFICIENT_FUNDS(2, "Non sufficient fund"),
  NON_SUFFICIENT_UNITS(3, "Non sufficient units"),
  USER_NOT_FOUND(4, "User not found"),
  INCORRECT_TIKECTMASTER_LAYOUT(5, "Incorrect ticketmaster layout"),
  INCORRECT_PARAMETER_REQUEST(6, "Incorrect paramater request"),
  NON_ADMINISTRATIVE_ACCOUNT_FOUND(7, "Non administrative accound found"),
  PROJECT_NOT_FOUND(8, "Project not found"),
  INCORRECT_OPERATION_STATUS(9, "Incorrect operation status"),
  FILE_NOT_FOUND(10, "File not found"),
  REDEMPTION_CODE_NOT_FOUND(11, "Redemption code not found"),
  CODE_ALREADY_USE(12, "Code already use"),
  SECTION_CODE_NOT_FOUND(13, "Section code not found"),
  NON_SUFFICIENT_FINANTIAL_DATA(14, "Non sufficient finantial data"),
  REDEMPTION_CODE_NOT_VALID(15, "Redemption code not valid"),
  PROVIDER_NOT_FOUND(16, "Provider not found"),
  ADVANCE_PAYMENT_ALREADY_PAID(17, "Advance payment already paid"),
  SETTLEMENT_ALREADY_PAID(18, "Settlement already paid"),
  FILE_SAVE_ERROR(19, "File save error"),
  ACCOUNT_NUMBER_NOT_VALID(20, "Account number not valid"),
  VARIABLE_COST_NOT_VALID(21, "Variable cost not valid"),
  ACCOUNT_NOT_FOUND(22, "Account not found"),
  STP_TRANSACTION_ERROR(23, "STP transaction error"),
  TRANSFER_AMOUNT_EXCEED(24, "Transfer amount exceed"),
  PAYPAL_ERROR(25, "Paypal error"),
  CLABE_ACCOUNT_DUPLICATE(26, "Clabe account duplicate"),
  INVALID_CLABE_ACCOUNT(27, "Invalid clabe account"),
  NOT_AVAILABLE_IN_THIS_TIME(28, "Not available in this time"),
  NOT_LIMIT_AMOUNT_REGISTER(29, "Not limit amount register"),
  INVALID_AMOUNT(30, "Invalid amount")

  private final int code
  private final String message

  TimoneErrorStatus(code, message) {
    this.code = code
    this.message = message
  }

  public int getValue() { return code }
  public String getMessage() { return message }

}
