package com.imart.order.dto.local;

import com.imart.order.dto.foreign.Address;
import com.imart.order.dto.foreign.Cart;
import com.imart.order.model.OrderStatus;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CheckOut {
    private String sessionId;
    private OrderStatus status;
    private Cart cart;
    private Address shippingAddress;
}
