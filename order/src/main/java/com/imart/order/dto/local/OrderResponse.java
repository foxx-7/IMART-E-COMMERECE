package com.imart.order.dto.local;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long userId;
    private Long cartId;
    private BigDecimal checkOutAmount;
    private String status;
    private Address shippingAddress;
    private LocalDateTime creationTimeStamp;
    private LocalDateTime updateTimestamp;
}
