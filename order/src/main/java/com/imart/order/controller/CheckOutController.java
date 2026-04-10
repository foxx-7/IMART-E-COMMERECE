package com.imart.order.controller;

import com.imart.order.dto.foreign.Address;
import com.imart.order.dto.foreign.CartItem;
import com.imart.order.dto.foreign.InventoryCheckResponse;
import com.imart.order.service.CheckOutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/checkout")
@RequiredArgsConstructor
public class CheckOutController {
    private final CheckOutService checkOutService;

    @PostMapping("/init")
    public ResponseEntity<?> initiateCheckOut(@RequestHeader("X-User-Id") Long userId, @RequestBody List<CartItem> items){
        return ResponseEntity.status(HttpStatus.OK).body(checkOutService.initializeCheckOut(userId, items));
    }

    @PostMapping("/carts/validate")
    public ResponseEntity<InventoryCheckResponse> addressCartState(
            @RequestHeader("X-User-Id") Long userId, @RequestHeader("Session-Id")String sessionId
            , @RequestBody @Valid InventoryCheckResponse inventoryCheckResponse){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(checkOutService.validateCart(userId, inventoryCheckResponse));
    }

    @GetMapping("/address")
    public ResponseEntity<List<Address>> getAvailableShippingAddresses(@RequestHeader("X-User-Id") Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(checkOutService.provideShippingAddress(userId));
    }

    @PostMapping("/add/shippingAddress")
    public ResponseEntity<Address> resolveShippingAddress(@RequestHeader("X-User-Id") Long userId, @RequestBody Address shippingAddress){
        return ResponseEntity.status(HttpStatus.CREATED).body(checkOutService.resolveShippingAddress(userId, shippingAddress));
    }

    @PostMapping("/payment")
    public ResponseEntity<Void> submitPayment(@RequestHeader("X-User-Id") Long userId){
        checkOutService.initializePayment(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

