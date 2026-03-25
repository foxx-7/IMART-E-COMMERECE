package com.imart.cart.dto.local;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

//**prevents consumers from breaking if dto gains a new field
@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckOutEvent {
    private CartDto  cartDto;
}
