package com.imart.order.dto.local;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.imart.order.dto.foreign.Cart;
import com.imart.order.dto.foreign.SubmittableItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

//**prevents consumers from breaking if dto gains a new field
@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckOutEvent {
    private SubmittableItem cart;
    private String sessionId;
}
