package com.api.cart.controller;

import com.api.cart.dto.CartItemRequest;
import com.api.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCart(
        @RequestHeader("user-id")  String userId,
        @RequestBody CartItemRequest request){

        if(!cartService.addToCart(Long.valueOf(userId), request)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("cart created successfully");
    }
}
