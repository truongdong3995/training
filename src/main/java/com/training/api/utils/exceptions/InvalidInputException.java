package com.training.api.utils.exceptions;

public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String msg){
        super(msg);
    }
}
