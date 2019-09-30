package com.training.api.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception when contrains error
 *
 */
public class ConflictException extends RuntimeException {
	
	public ConflictException(String message) {
		super(message);
	}
	
	public ConflictException(String message, Throwable cause) {
		super(message, cause);
	}
}
