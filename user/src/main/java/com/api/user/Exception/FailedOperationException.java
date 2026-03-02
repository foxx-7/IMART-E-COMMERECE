package com.api.user.Exception;

public class FailedOperationException extends RuntimeException {
    public FailedOperationException(String message) {
        super(message);
    }
}
