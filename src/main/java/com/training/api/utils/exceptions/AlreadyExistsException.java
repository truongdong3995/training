package com.training.api.utils.exceptions;

/**
 * Exception when constraint error
 *
 */
public class AlreadyExistsException extends RuntimeException {
	/**
	 * Create instance
	 *
	 * @param message message
	 */
	public AlreadyExistsException(String message) {
		super(message);
	}
	
	public AlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}
}
