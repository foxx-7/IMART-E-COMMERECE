package com.imart.cart.utility;


import com.imart.cart.dto.local.CartItemResponse;
import com.imart.cart.model.CartItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CartItemResponseMapper {
    public static CartItemResponse mapToCartItemResponse(CartItem cartItem){
        return CartItemResponse.builder()
                .price(cartItem.getPrice())
                .productId(cartItem.getProductId())
                .cumulativePrice(cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .quantity(cartItem.getQuantity())
                .build();
    }
}
