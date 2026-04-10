package com.imart.order.exception;

public class RecordAlreadyExistingException extends RuntimeException {
    public RecordAlreadyExistingException(String message) {
        super(message);
    }
}
