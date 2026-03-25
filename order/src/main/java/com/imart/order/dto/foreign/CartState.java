package com.imart.order.dto.foreign;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartState implements SubmittableItem{
    private Long userId;
    private Long id;
    private BigDecimal subTotal;
    private List<InventoryCheckResponse.AvailableItem> availableItemList;
    private List<InventoryCheckResponse.OutOfStockItem> outOfStockItems;

    //interface contract implementation
    @Override
    public List<CartItemResponse> getItems() {
        return List.of();
    }
}
