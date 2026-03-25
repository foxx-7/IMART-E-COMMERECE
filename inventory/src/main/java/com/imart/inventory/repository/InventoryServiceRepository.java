package com.imart.inventory.repository;

import com.imart.inventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryServiceRepository extends  JpaRepository<Inventory, Long> {
    Optional<Inventory> findByProductId(Long productId);
}
