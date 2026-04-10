package com.imart.order.handler;

import com.imart.order.dto.local.ErrorResponse;
import com.imart.order.exception.*;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException e){
        //builder pattern used here to set error fields
        ErrorResponse response = ErrorResponse.builder()
                .error("resource not found")
                .message(e.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .timeStamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(response.getStatus()).body(response);
    }

        @ExceptionHandler(UnprocessableRequestException.class)
        public ResponseEntity<ErrorResponse> handleUnprocessableRequest(UnprocessableRequestException e){
            ErrorResponse response = ErrorResponse.builder()
                    .error("resource not found")
                    .message(e.getMessage())
                    .status(HttpStatus.UNPROCESSABLE_CONTENT)
                    .timeStamp(LocalDateTime.now())
                    .build();

            return ResponseEntity.status(response.getStatus()).body(response);
        }

    @ExceptionHandler(SessionTimeOutException.class)
    public ResponseEntity<ErrorResponse> handleSessionTimeOut(SessionTimeOutException e){
        ErrorResponse response = ErrorResponse.builder()
                .error("session timeout")
                .message(e.getMessage())
                .status(HttpStatus.GONE)
                .timeStamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(RecordAlreadyExistingException.class)
    public ResponseEntity<ErrorResponse> handleAlreadyExistingRecord(RecordAlreadyExistingException e){
        //builder pattern used here to set error fields
        ErrorResponse response = ErrorResponse.builder()
                .error("record already exists")
                .message(e.getMessage())
                .status(HttpStatus.CONFLICT)
                .timeStamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(InvalidAddressException.class)
    public ResponseEntity<ErrorResponse> handleInvalidAddress(InvalidAddressException e){
        ErrorResponse response = ErrorResponse.builder()
                .error("invalid address")
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .timeStamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(InvalidSessionException.class)
    public ResponseEntity<ErrorResponse> handleInvalidSession(InvalidSessionException e){
        ErrorResponse response = ErrorResponse.builder()
                .error("invalid address")
                .message(e.getMessage())
                .status(HttpStatus.UNAUTHORIZED)
                .timeStamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(PendingActiveSessionException.class)
    public ResponseEntity<ErrorResponse> handlePendingSession(PendingActiveSessionException e){
        ErrorResponse response = ErrorResponse.builder()
                .error("invalid address")
                .message(e.getMessage())
                .status(HttpStatus.CONFLICT)
                .timeStamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
