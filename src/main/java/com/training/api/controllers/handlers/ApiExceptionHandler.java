package com.training.api.controllers.handlers;

import com.training.api.utils.ApiMessage;
import com.training.api.utils.RestData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleAllException() {
        return new ResponseEntity<>(ApiMessage.error500(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
