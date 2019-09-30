package com.training.api.utils.exceptions;


/**
 * Exception when contrains error
 *
 */
public class AlreadyExistsException extends RuntimeException {
	
	public AlreadyExistsException(String message) {
		super(message);
	}
	
	public AlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}
}
