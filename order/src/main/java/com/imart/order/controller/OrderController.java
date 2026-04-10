package com.imart.order.controller;

import com.imart.order.dto.foreign.Address;
import com.imart.order.dto.local.OrderResponse;
import com.imart.order.service.OrderService;
import com.imart.order.utility.OrderResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/active")
    public ResponseEntity<List<OrderResponse>> getActiveOrders(@RequestHeader("X-User-Id") Long userId){
        List<OrderResponse> orderList = orderService.fetchActiveOrder(userId).stream()
                .map(OrderResponseMapper::mapToOrderResponse)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(orderList);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrderHistory(@RequestHeader("X-User-Id") Long userId){
        List<OrderResponse> orderList = orderService.fetchAll(userId).stream()
                .map(OrderResponseMapper::mapToOrderResponse)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(orderList);
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<Void> removeOrderItem(@RequestHeader("X-User-Id") Long userId ,@PathVariable Long productId){
        orderService.removeItemFromOrder(userId, productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/address")
    public ResponseEntity<Void> updateOrderShippingAddress(@RequestHeader("X-User-Id")Long userId, @RequestBody Address addressUpdate){
       orderService.updateShippingAddress(userId, addressUpdate);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/deactivate")
    public ResponseEntity<Void> deactivateOrder(@RequestHeader("X-User-Id") Long userId, @RequestHeader("X-checkout-Id") String checkOutId){
        orderService.deactivateOrder(userId,checkOutId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
