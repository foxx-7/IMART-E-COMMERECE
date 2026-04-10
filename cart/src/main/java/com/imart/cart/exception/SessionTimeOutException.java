package com.imart.cart.exception;

public class SessionTimeOutException extends RuntimeException {
    public SessionTimeOutException(String message) {
        super(message);
    }
}
