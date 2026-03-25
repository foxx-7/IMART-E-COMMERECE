package com.imart.user.exception;

public class UncompletableActionException extends RuntimeException {
    public UncompletableActionException(String message) {
        super(message);
    }
}
