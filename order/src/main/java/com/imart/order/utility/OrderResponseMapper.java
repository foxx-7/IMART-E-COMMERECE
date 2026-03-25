package com.imart.order.utility;

import com.imart.order.dto.local.OrderResponse;
import com.imart.order.model.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderResponseMapper {

    public static OrderResponse mapToOrderResponse(Order order){

        return OrderResponse.builder()
                .userId(order.getUserId())
                .checkOutAmount(order.getCheckOutAmount())
                .creationTimeStamp(order.getCreationTimeStamp())
                .updateTimestamp(order.getUpdateTimeStamp())
                .cartId(order.getCartId())
                .status(String.valueOf(order.getStatus()))
                .build();
    }
}
