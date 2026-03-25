package com.imart.order.dto.foreign;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class InventoryCheckResponse {
    private boolean fullyAvailable;
    private List<AvailableItem> availableItemsList;
    private List<OutOfStockItem> outOfStockItemsList;
    private String reservationId;


    @Data
    public class AvailableItem{
        private Long productId;
        private String productName;
        private long requestedQuantity;
        private long reservedQuantity;
        private BigDecimal price;
    }

    @Data
    public class OutOfStockItem{
        private Long productId;
        private String productName;
        private long requestedQuantity;
        private long availableQuantity;
        private BigDecimal price;
        private String message;

    }

}
