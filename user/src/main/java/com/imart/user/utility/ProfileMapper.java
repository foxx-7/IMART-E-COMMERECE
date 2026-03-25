package com.imart.user.utility;

import com.imart.user.dto.ProfileRequest;
import com.imart.user.model.UserProfile;

public class ProfileMapper {
    public static  UserProfile mapToProfile(ProfileRequest request) {
        return UserProfile.builder()
                .userName(request.getUserName())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .address(request.getAddress())
                .contact(request.getContact())
                .build();
    }
}
