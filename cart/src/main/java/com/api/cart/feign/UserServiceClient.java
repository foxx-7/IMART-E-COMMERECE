package com.api.cart.feign;

import com.api.cart.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user_service")
public interface UserServiceClient {

    @GetMapping
    UserResponse getUserById(@PathVariable Long id);
}
