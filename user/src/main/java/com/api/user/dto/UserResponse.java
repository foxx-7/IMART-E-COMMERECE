package com.api.user.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String contact;
    private String role;
    private LocalDateTime creationTime;
    private String address;
    private LocalDateTime updateTime;
}
