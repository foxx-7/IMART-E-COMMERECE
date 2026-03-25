package com.imart.product.handler;

import com.imart.product.exception.AlreadyInUseException;
import com.imart.product.exception.FailedOperationException;
import com.imart.product.exception.InvalidUpdateFieldException;
import com.imart.product.exception.NotFoundException;
import com.imart.product.dto.ErrorResponse;
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
                .error("already in use")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST)
                .build();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(NotFoundException e){
        ErrorResponse response  = ErrorResponse.builder()
                .error("not found")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND)
                .build();
        return ResponseEntity.status(response.getStatus()).body(response);

    }

    @ExceptionHandler(FailedOperationException.class)
    public ResponseEntity<ErrorResponse> handleUncompletableAction(FailedOperationException e){
        ErrorResponse response  = ErrorResponse.builder()
                .error("failed operation")
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

    @ExceptionHandler(InvalidUpdateFieldException.class)
    public ResponseEntity<ErrorResponse> handleInvalidUpdateField(InvalidUpdateFieldException e){
        ErrorResponse response = new ErrorResponse();
        response.setTimestamp(LocalDateTime.now());
        response.setError("invalid update fields");
        response.setMessage(e.getMessage());
        response.setStatus(HttpStatus.BAD_REQUEST);

        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
