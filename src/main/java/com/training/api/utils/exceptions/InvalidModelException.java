package com.training.api.utils.exceptions;

/**
 * Exception when model invalid error
 *
 */
public class InvalidModelException extends RuntimeException {
	
	/**
	 * Create instance with detailed message.
	 *
	 * @param message the detailed message
	 */
	public InvalidModelException(String message) {
		super(message);
	}
	
	/**
	 * Create instance with detailed message.
	 *
	 * @param message the detailed message
	 */
	public InvalidModelException(String message, Throwable cause) {
		super(message, cause);
	}
}
