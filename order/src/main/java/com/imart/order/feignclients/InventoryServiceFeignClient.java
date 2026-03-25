package com.imart.order.feignclients;

import com.imart.order.dto.foreign.CartItemResponse;
import com.imart.order.dto.foreign.InventoryCheckResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Component
@FeignClient(name = "inventory-service")
public interface InventoryServiceFeignClient {

    @GetMapping("/api/v1/inventory")
    InventoryCheckResponse processStockAvailability(@RequestBody
    List<CartItemResponse> cartItems);
}
