package com.api.user.utility;

import com.api.user.dto.UserRequest;
import com.api.user.model.User;

public class UserMapper {
    public static  User mapToUser(UserRequest request) {
    User user = new User();

    user.setUserName(request.getUserName());
    user.setFirstName(request.getFirstName());
    user.setLastName(request.getLastName());
    user.setEmail(request.getEmail());
    user.setContact(request.getContact());

    if (request.getAddress() != null) {
        user.setAddress(request.getAddress());
    }
    return user;
}
}
