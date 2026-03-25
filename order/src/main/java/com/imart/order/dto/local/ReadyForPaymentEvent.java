package com.imart.order.dto.local;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
public class ReadyForPaymentEvent {
    private Long userId;
    private String sessionId;
    private BigDecimal TotalAmount;
}
