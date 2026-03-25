package com.imart.cart.repository;

import com.imart.cart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByUserIdAndProductId(Long userId, Long productId);
    void deleteByUserIdAndProductId(Long userId, Long productId);
    List<CartItem> findByUserId(Long userId);
    boolean existsByUserIdAndProductId(Long userId, Long productId);
    boolean existsByUserId(Long userId);
    void deleteByUserId(Long userId);
}
