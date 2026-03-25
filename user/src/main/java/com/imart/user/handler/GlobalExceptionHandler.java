package com.imart.user.handler;

import com.imart.user.exception.AlreadyInUseException;
import com.imart.user.exception.UncompletableActionException;
import com.imart.user.exception.NotFoundException;
import com.imart.user.dto.ErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyInUseException.class)
    public ResponseEntity<ErrorResponse> handleAlreadyInUse(AlreadyInUseException e){

        ErrorResponse response  = ErrorResponse.builder()
                .error("resource already exists")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT)
                .build();
       return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException e){
        ErrorResponse response  = ErrorResponse.builder()
                .error("resource not found ")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND)
                .build();

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(UncompletableActionException.class)
    public ResponseEntity<ErrorResponse> handleUncompletableAction(UncompletableActionException e){
        ErrorResponse response  = ErrorResponse.builder()
                .error("action could not be completed ")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.UNPROCESSABLE_CONTENT)
                .build();

        return ResponseEntity.status(response.getStatus()).body(response);

    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException e){

        ErrorResponse response  = ErrorResponse.builder()
                .error("data integrity violation")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT)
                .build();

        return ResponseEntity.status(response.getStatus()).body(response);

    }

}
