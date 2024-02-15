package com.Bcm.Exception;

import lombok.Getter;

public class InvalidDate extends RuntimeException {

    @Getter
    final String errorMessage;

    public InvalidDate(String errorMessages) {
        super();
        errorMessage = errorMessages;
    }
}
