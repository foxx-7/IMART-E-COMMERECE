package com.imart.order.exception;

import com.imart.order.dto.local.CheckOut;
import com.imart.order.dto.local.PendingCheckOut;
import lombok.Getter;

@Getter
public class PendingActiveSessionException extends RuntimeException {
    private final PendingCheckOut checkOut;
    private final String message;
    public PendingActiveSessionException(String message, PendingCheckOut checkOut) {
       this.checkOut = checkOut;
        this.message = message;
    }
}
