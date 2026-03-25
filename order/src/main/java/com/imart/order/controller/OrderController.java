package com.imart.order.controller;

import com.imart.order.dto.foreign.Address;
import com.imart.order.dto.foreign.Cart;
import com.imart.order.dto.foreign.CartItemResponse;
import com.imart.order.dto.local.OrderRequest;
import com.imart.order.dto.local.OrderResponse;
import com.imart.order.service.OrderService;
import com.imart.order.utility.OrderResponseMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderResponse> getUserOrder
            (@RequestHeader("X-User-Id") Long userId, @PathVariable Long orderId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(OrderResponseMapper.mapToOrderResponse(orderService.fetchUserOrder(userId, orderId)));
    }

    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<String> deleteUserOrder(@RequestHeader("X-User-Id") Long userId, @PathVariable Long orderId) {
        orderService.cancelOrder(userId, orderId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("order for user: " + userId + " canceled successfully");
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderResponse> createUserOrder(@RequestHeader("X-User-Id") Long userId, @RequestBody @Valid OrderRequest request){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(orderService.createOrder(userId, request));
    }
}

