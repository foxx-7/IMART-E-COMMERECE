package com.imart.order.exception;

public class InvalidCartStateException extends RuntimeException {
  public InvalidCartStateException(String message) {
    super(message);
  }
}
