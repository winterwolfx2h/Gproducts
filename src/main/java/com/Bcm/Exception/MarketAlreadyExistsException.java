package com.Bcm.Exception;

public class MarketAlreadyExistsException extends RuntimeException {
    public MarketAlreadyExistsException(String message) {
        super(message);
    }
}
