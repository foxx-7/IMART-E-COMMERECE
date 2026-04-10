package com.imart.user.utility;

import com.imart.user.dto.ProfileResponse;
import com.imart.user.model.UserProfile;
import lombok.Data;

public class ProfileResponseMapper {

    public static ProfileResponse mapToProfileResponse(UserProfile profile) {
        return ProfileResponse.builder()
                .userName(profile.getUserName())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .email(profile.getEmail())
                .contact(profile.getContact())
                .creationTime(profile.getCreationTime())
                .updateTime(profile.getUpdateTime())
                .build();
    }
}