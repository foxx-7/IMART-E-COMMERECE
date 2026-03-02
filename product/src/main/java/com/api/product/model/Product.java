package com.api.product.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name="products_table")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

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
    @CreationTimestamp
    private LocalDateTime creationTimestamp;
    @UpdateTimestamp
    private LocalDateTime updateTimestamp;

}
