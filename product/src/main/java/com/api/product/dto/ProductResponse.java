package com.api.product.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;

@Data
public class ProductResponse {


    @NotNull(message="name field cannot be null")
    private String name;

    @NotNull(message=" product brand must be provided")
    private String brand;
    @NotNull(message="price field cannot be null")
    private BigDecimal price;
    @NotNull(message="stockQuantity field cannot be null")
    private Long stockQuantity;
    @Size(min=20, max=250)
    private String description;

}
