package com.Bcm.Exception;

public class BusinessLogicException extends RuntimeException {

  public BusinessLogicException() {
    super("A business logic exception occurred.");
  }

  public BusinessLogicException(String message) {
    super(message);
  }

  public BusinessLogicException(String message, Throwable cause) {
    super(message, cause);
  }

  public BusinessLogicException(Throwable cause) {
    super(cause);
  }
}
