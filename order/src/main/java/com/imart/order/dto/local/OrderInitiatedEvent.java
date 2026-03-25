package com.imart.order.dto.local;

import com.imart.order.dto.foreign.CartItemResponse;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class OrderInitiatedEvent {
    private List<CartItemResponse> cartItems;
    private Long userId;
}
