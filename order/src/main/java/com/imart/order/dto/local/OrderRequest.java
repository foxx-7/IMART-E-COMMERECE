package com.imart.order.dto.local;

import com.imart.order.dto.foreign.Address;
import com.imart.order.dto.foreign.CartItem;
import com.imart.order.model.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private Long userId;

    private String checkOutSessionId;

    private UUID checkOutId;

    private List<CartItem> items;

    private String transactionId;

    @NotBlank(message = "shipping address must be provided")
    private Address shippingAddress;

    @NotBlank(message = "total sum must be provided")
    private BigDecimal totalAmount;

    private OrderStatus status;
}
