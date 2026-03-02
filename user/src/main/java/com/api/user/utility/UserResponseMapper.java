package com.api.user.utility;

import com.api.user.dto.UserResponse;
import com.api.user.model.User;

public class UserResponseMapper {

    public  UserResponse mapToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();

        userResponse.setUserName(user.getUserName());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setContact(user.getContact());
        userResponse.setCreationTime(user.getCreationTime());
        userResponse.setUpdateTime(user.getUpdateTime());
        userResponse.setRole(String.valueOf(user.getRole()));

        if (user.getAddress() != null) {
            userResponse.setAddress(user.getAddress());
        }
        return userResponse;
    }
}