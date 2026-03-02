package com.api.user.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String contact;
    private String role;
    private String address;
}
