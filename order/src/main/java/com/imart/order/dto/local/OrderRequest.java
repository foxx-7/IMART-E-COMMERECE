package com.imart.order.dto.local;

import com.imart.order.dto.foreign.Cart;
import com.imart.order.dto.foreign.CartItemResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderRequest {
    @NotBlank(message = "valid cart must be provided")
    private Cart cart;

    @NotBlank(message = "total sum must be provided")
    private BigDecimal totalAmount;
}
