package com.imart.cart.utility;


import com.imart.cart.dto.local.CartItemResponse;
import com.imart.cart.model.CartItem;
import org.springframework.stereotype.Component;

@Component
public class CartItemResponseMapper {
    public static CartItemResponse mapToCartItemResponse(CartItem cartItem){
        return CartItemResponse.builder()
                .cumulativePrice(cartItem.getCumulativePrice())
                .productId(cartItem.getProductId())
                .quantity(cartItem.getQuantity())
                .build();
    }
}
