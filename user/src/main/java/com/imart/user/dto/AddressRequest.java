package com.imart.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddressRequest {

    private Long userId;

    @NotBlank(message = "first name must be provided")
    private String firstName;

    @NotBlank(message = "last name must be provided")
    private String lastName;

    @NotBlank(message = "phone number must be provided")
    private String phoneNumber;

    @NotBlank(message = "email must be provided")
    private String email;

    @NotBlank(message = "zipcode must be provided")
    private String zipCode;

    @NotBlank(message = "street address cannot be blank")
    private String street;

    @NotBlank(message = "country must be provided")
    private String country;

    @NotBlank(message = "state must be provided")
    private String state;

    @NotBlank(message = "city cannot be blank")
    private String city;

    private boolean isValidated;
    private boolean isDefault;
}
