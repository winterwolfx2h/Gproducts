package com.Bcm.Exception;

import lombok.Getter;

public class AlreadyExistException extends RuntimeException {

    @Getter
    final String errorMessage;

    public AlreadyExistException(String errorMessages) {
        super();
        errorMessage = errorMessages;
    }
}
