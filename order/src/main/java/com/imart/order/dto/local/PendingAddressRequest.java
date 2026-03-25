package com.imart.order.dto.local;

import com.imart.order.dto.foreign.Address;
import com.imart.order.dto.foreign.Cart;
import com.imart.order.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PendingAddressRequest {
    private String checkOutSessionId;
    private Cart cart;
    private OrderStatus status;
    private List<Address> availableAddresses;
    private boolean requiresAddressChoice;
    private boolean requiresNewAddress;

    public static boolean setAddressRequirement(List<Address> addresses){
        return addresses.isEmpty();
    }
}
