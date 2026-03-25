package com.imart.inventory.controller;

import com.imart.inventory.dto.ProductInventory;
import com.imart.inventory.model.Inventory;
import com.imart.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/v1/inventory/{productId}")
    public ResponseEntity<ProductInventory> getProductInventory(@PathVariable Long productId){
        return ResponseEntity.status(HttpStatus.OK).body(inventoryService.fetchProductInventory(productId));
    }

    @GetMapping("/v1/inventory")
    public ResponseEntity<List<Inventory>> getAllProductInventory(){
        return ResponseEntity.status(HttpStatus.OK).body(inventoryService.getAllInventories());
    }
}
