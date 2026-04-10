package com.imart.order.dto.local;

import com.imart.order.dto.foreign.Address;
import com.imart.order.dto.foreign.CartItem;
import com.imart.order.model.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class CheckOut {
    private Long userId;
    private String checkOutSessionId;
    private List<CartItem> items;
    private BigDecimal total;
    private Address shippingAddress;
    private OrderStatus status;
    private String transactionId;
    private LocalDateTime creationTimestamp;
}
