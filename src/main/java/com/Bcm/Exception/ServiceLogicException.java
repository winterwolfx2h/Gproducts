package com.Bcm.Exception;

public class ServiceLogicException extends RuntimeException {

  public ServiceLogicException() {
    super("A service logic exception occurred.");
  }

  public ServiceLogicException(String message) {
    super(message);
  }

  public ServiceLogicException(String message, Throwable cause) {
    super(message, cause);
  }

  public ServiceLogicException(Throwable cause) {
    super(cause);
  }
}
