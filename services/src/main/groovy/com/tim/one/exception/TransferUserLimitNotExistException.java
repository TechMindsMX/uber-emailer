package com.tim.one.exception;

public class TransferUserLimitNotExistException extends RuntimeException {

  private static final long serialVersionUID = 5307275148573017923L;
  
  private Integer userId;
  private Integer destinationId;

  public TransferUserLimitNotExistException(Integer userId, Integer destinationId) {
    this.userId = userId;
    this.destinationId = destinationId;
	}

	@Override
	public String getMessage() {
		return "TRANSFER USER LIMIT W/USERID: " + userId + " AND DESTINATIONID:" + destinationId + " NOT EXISTS";
	}

}
