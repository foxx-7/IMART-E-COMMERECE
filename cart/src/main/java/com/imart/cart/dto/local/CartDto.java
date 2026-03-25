package com.imart.cart.dto.local;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private Long userId;
    private BigDecimal subtotal;
    private Long cartId;
    private List<CartItemResponse> items;
}
