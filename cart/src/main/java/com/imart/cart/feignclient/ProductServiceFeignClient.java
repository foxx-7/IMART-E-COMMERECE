package com.imart.cart.feignclient;

import com.imart.cart.dto.foreign.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
@Component
public interface ProductServiceFeignClient {
    @GetMapping("/api/v1/products/{productId}")
    Product getProductById(@PathVariable Long productId);
}
