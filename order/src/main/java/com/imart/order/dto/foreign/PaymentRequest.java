package com.imart.order.dto.foreign;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Data
public class PaymentRequest {
    private String checkOutSessionId;
    private Long userId;
    private BigDecimal amount;
    private LocalDateTime timeStamp;
}
