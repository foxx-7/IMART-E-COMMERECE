package com.imart.cart.dto.local;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CartItemRequest {
    @NotBlank(message = "product id not provided")
    private Long productId;
    @NotBlank(message = "stock quantity must be provided here")
    private long quantity;
}
