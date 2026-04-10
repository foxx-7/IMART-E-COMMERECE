package com.imart.user.utility;

import com.imart.user.dto.AddressRequest;
import com.imart.user.model.Address;
import lombok.Data;

@Data
public class AddressMapper {

    public static Address mapToAddress(AddressRequest request){
        return Address.builder()
                .userId(request.getUserId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .country(request.getCountry())
                .state(request.getState())
                .zipCode(request.getZipCode())
                .street(request.getStreet())
                .build();
    }
}
