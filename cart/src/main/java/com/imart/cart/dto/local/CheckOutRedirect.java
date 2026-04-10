package com.imart.cart.dto.local;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CheckOutRedirect {
    private Long userId;
    private List<CartItemResponse> items;
    private String redirectUrl;
}
