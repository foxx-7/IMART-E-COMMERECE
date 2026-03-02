package com.api.product.repository;

import com.api.product.model.Product;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface ProductRepository extends JpaRepository<@NonNull  Product, @NonNull Long> {
    public boolean existsByName(String name);
    public boolean existsByBrand(String brand);
    public boolean existsByPrice(BigDecimal price);
}