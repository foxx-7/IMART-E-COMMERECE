package com.imart.order.dto.foreign;

import java.math.BigDecimal;
import java.util.List;

public interface SubmittableItem {
    Long getUserId();
    BigDecimal getSubTotal();
    Long getId();

    List<CartItemResponse> getItems();
}
