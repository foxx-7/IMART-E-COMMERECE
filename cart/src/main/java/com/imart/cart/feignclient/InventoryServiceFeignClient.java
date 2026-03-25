package com.imart.cart.feignclient;

import com.imart.cart.dto.foreign.ProductInventory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "inventory-service")
@Component
public interface InventoryServiceFeignClient {

    @GetMapping("/api/v1/inventory/{productId}")
    ProductInventory getProductInventory(@PathVariable Long productId);
}
