package com.imart.order.dto.local;

import com.imart.order.dto.foreign.Address;
import com.imart.order.dto.foreign.CartItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long userId;
    private List<CartItem> items;
    private BigDecimal checkOutAmount;
    private String transactionId;
    private String checkOutId;
    private String status;
    private Address shippingAddress;
    private LocalDateTime creationTimeStamp;
    private LocalDateTime updateTimestamp;
}
