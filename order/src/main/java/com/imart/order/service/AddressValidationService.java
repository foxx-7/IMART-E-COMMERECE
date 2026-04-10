package com.imart.order.service;


import com.imart.order.dto.foreign.Address;
import org.springframework.stereotype.Service;

@Service
public class AddressValidationService {

    public boolean validateAddress(Address address){
        return true;
    }
}
