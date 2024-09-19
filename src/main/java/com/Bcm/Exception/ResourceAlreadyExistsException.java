package com.Bcm.Exception;

public class ResourceAlreadyExistsException extends RuntimeException {

  public ResourceAlreadyExistsException() {
    super("POPlan already exists");
  }

  public ResourceAlreadyExistsException(String message) {
    super(message);
  }

  public ResourceAlreadyExistsException(String message, Throwable cause) {
    super(message, cause);
  }

  public ResourceAlreadyExistsException(Throwable cause) {
    super("POPlan already exists", cause);
  }
}
