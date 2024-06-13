package com.Bcm.Exception;

public class BusinessProcessAlreadyExistsException extends RuntimeException {
  public BusinessProcessAlreadyExistsException(String message) {
    super(message);
  }
}
