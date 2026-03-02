package com.api.cart.service;

import com.api.cart.dto.CartItemRequest;
import com.api.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final  CartRepository cartRepository;

    public boolean addToCart(Long userId, CartItemRequest request){
        return true;
    }
}
