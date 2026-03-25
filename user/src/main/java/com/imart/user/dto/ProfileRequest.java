package com.imart.user.dto;

import lombok.Data;

@Data
public class ProfileRequest {
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String contact;
    private String address;
}
