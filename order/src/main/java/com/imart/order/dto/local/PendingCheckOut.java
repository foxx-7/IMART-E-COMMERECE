package com.imart.order.dto.local;

import lombok.Data;

@Data
public class PendingCheckOut {
    private CheckOut checkOut;
    private String resumptionUrl;
}
