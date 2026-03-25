package com.imart.cart.dto.foreign;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ProductInventory {
    private long AvailableStockQuantity;
    private long ReservedStockQuantity;
}
