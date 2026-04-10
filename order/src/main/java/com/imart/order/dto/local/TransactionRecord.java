package com.imart.order.dto.local;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRecord {
    private String transactionId;
    private Long userId;
    private BigDecimal Amount;
    private TransactionStatus status;
}
