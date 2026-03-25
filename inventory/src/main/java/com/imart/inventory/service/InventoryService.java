package com.imart.inventory.service;

import com.imart.inventory.dto.ProductInventory;
import com.imart.inventory.exception.ResourceNotFoundException;
import com.imart.inventory.model.Inventory;
import com.imart.inventory.repository.InventoryServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryServiceRepository inventoryServiceRepository;

    //fetch product inventory
    public ProductInventory fetchProductInventory(Long productId){
       Optional<Inventory> inventoryOpt = inventoryServiceRepository.findByProductId(productId);
       if (inventoryOpt.isEmpty()){
           throw new ResourceNotFoundException("product inventory does not exist");
       }
       Inventory inventory = inventoryOpt.get();
       ProductInventory productInventory = new ProductInventory();
       productInventory.setAvailableStockQuantity(inventory.getAvailableStockQuantity());
       productInventory.setReservedStockQuantity(inventory.getReservedStockQuantity());

       return productInventory;
    }

    //fetch all product inventories....admin only
    public List<Inventory> getAllInventories(){
        return inventoryServiceRepository.findAll();
    }

}
