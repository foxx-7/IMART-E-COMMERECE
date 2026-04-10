package com.imart.order.dto.foreign;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
private Long userId;
private Long productId;
private BigDecimal price;
private BigDecimal cumulativePrice;
private long quantity;
}
