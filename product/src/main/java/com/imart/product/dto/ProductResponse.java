package com.imart.product.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductResponse {


    @NotNull(message="name field cannot be null")
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private Long stockQuantity;
    private String description;
    private LocalDateTime creationTimeStamp;
    private LocalDateTime updateTimeStamp;
}
