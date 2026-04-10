package com.imart.order.repository;

import com.imart.order.model.Order;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUserId(Long userId);
    Optional<Order> findByUserId(Long userId);
    List<Order> findAllByUserIdAndIsActiveTrue(Long userId);
    Optional<Order> findByUserIdAndCheckOutIdAndIsActiveTrue(Long userId, UUID checkOutId);
}
