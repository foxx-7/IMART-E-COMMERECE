package com.imart.order.dto.foreign;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.boot.convert.DataSizeUnit;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Cart implements SubmittableItem{
    @NotBlank(message = "user id cannot be empty")
    public Long userId;

    @NotBlank(message = "cart id must be provided")
    public Long id;

    @NotBlank(message = "cart items must be provided")
    @NotEmpty(message = "list cannot be empty")
    private List<CartItemResponse> items;

    @NotBlank(message = "sub total must be provided")
    private BigDecimal subTotal;
}
