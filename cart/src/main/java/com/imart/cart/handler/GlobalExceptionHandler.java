package com.imart.cart.handler;

import com.imart.cart.dto.local.ErrorResponse;
import com.imart.cart.exception.NotFoundException;
import com.imart.cart.exception.ResourceUnavaliableException;
import com.imart.cart.exception.UncompleteableActionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(NotFoundException e){
        ErrorResponse response = new ErrorResponse();

        response.setError("resource not found");
        response.setMessage(e.getMessage());
        response.setTimestamp(LocalDateTime.now());
        response.setStatus(HttpStatus.NOT_FOUND);

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(ResourceUnavaliableException.class)
    public ResponseEntity<ErrorResponse> handleResourceUnavailable(ResourceUnavaliableException e){
        ErrorResponse response = new ErrorResponse();

        response.setError("resource not found");
        response.setMessage(e.getMessage());
        response.setTimestamp(LocalDateTime.now());
        response.setStatus(HttpStatus.NOT_FOUND);

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(UncompleteableActionException.class)
    public ResponseEntity<ErrorResponse> handleUncompletableAction(UncompleteableActionException e){
        ErrorResponse response = new ErrorResponse();

        response.setError("resource not found");
        response.setMessage(e.getMessage());
        response.setTimestamp(LocalDateTime.now());
        response.setStatus(HttpStatus.EXPECTATION_FAILED);

        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
