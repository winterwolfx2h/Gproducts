package com.Bcm.Exception;
public class ServiceAlreadyExistsException extends RuntimeException {

    public ServiceAlreadyExistsException() {
        super("Service with the same name already exists");
    }

    public ServiceAlreadyExistsException(String message) {
        super(message);
    }

    public ServiceAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceAlreadyExistsException(Throwable cause) {
        super("Service with the same name already exists", cause);
    }
}
