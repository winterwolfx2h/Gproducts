package com.Bcm.Exception;

public class ProductOfferingLogicException extends RuntimeException {

  public ProductOfferingLogicException() {
    super("A business logic exception occurred.");
  }

  public ProductOfferingLogicException(String message) {
    super(message);
  }

  public ProductOfferingLogicException(String message, Throwable cause) {
    super(message, cause);
  }

  public ProductOfferingLogicException(Throwable cause) {
    super(cause);
  }
}
