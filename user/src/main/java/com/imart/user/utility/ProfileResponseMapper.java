package com.imart.user.utility;

import com.imart.user.dto.ProfileResponse;
import com.imart.user.model.UserProfile;

public class ProfileResponseMapper {

    public static ProfileResponse mapToProfileResponse(UserProfile profile) {
        return ProfileResponse.builder()
                .userName(profile.getUserName())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .email(profile.getEmail())
                .address(profile.getAddress())
                .contact(profile.getContact())
                .build();
    }
}