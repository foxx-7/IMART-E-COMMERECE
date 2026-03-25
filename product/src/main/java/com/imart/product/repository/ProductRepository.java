package com.imart.product.repository;

import com.imart.product.model.Product;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface ProductRepository extends JpaRepository< Product, Long> {
    boolean existsById(Long productId);
}