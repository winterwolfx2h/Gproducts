package com.Bcm.Exception;

import lombok.Getter;

public class NoSuchElementException extends RuntimeException{

	@Getter
	final String errorMessage;
	public NoSuchElementException(String errorMessages) {
		super();
		errorMessage = errorMessages;
	}

}
