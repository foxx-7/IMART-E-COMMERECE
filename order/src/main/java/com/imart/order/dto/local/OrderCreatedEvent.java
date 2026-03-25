package com.imart.order.dto.local;

import com.imart.order.dto.foreign.Cart;
import com.imart.order.dto.foreign.CartItemResponse;
import com.imart.order.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderCreatedEvent {
    private Long userId;
    private Cart cart;
    private List<CartItemResponse> items;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private LocalDateTime creationTimeStamp;
}
