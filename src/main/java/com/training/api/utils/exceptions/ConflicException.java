package com.training.api.utils.exceptions;

/**
 * Exception when contrains error
 *
 */
public class ConflicException extends RuntimeException {
    public ConflicException(String message) {
        super(message);
    }
}
