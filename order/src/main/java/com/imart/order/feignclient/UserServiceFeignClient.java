package com.imart.order.feignclient;

import com.imart.order.dto.foreign.Address;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "user-service")
@Component
public interface UserServiceFeignClient {
    @GetMapping("/api/v1/users/address")
    List<Address> getAvailableShippingAddresses(@RequestHeader("X-User-Id")Long userId);

    @GetMapping("/api/v1/users/loginStatus")
    boolean isLoggedIn(@RequestHeader("X-User-Id") Long userId);
}
