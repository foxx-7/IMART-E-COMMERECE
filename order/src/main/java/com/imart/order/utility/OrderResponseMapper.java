package com.imart.order.utility;

import com.imart.order.dto.local.OrderResponse;
import com.imart.order.model.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderResponseMapper {

    public static OrderResponse mapToOrderResponse(Order order){

        return OrderResponse.builder()
                .userId(order.getUserId())
                .items(order.getItems())
                .checkOutAmount(order.getCheckOutAmount())
                .creationTimeStamp(order.getCreationTimeStamp())
                .updateTimestamp(order.getUpdateTimeStamp())
                .build();
    }
}
