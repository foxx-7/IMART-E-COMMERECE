package com.imart.user.utility;

import com.imart.user.model.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ProfileUpdateMapper {
    private final ProfileValidator validator;
    public void updateProfileData(UserProfile existingProfile, UserProfile profileUpdate){
        if(profileUpdate.getFirstName() != null){
            existingProfile.setFirstName(profileUpdate.getFirstName());
        }
        if(profileUpdate.getLastName() != null){
            existingProfile.setLastName(profileUpdate.getLastName());
        }
        if(profileUpdate.getContact() != null){
            existingProfile.setContact(profileUpdate.getContact());
        }
        if(profileUpdate.getEmail() != null){
            existingProfile.setEmail(profileUpdate.getEmail());
        }
        if(profileUpdate.getAddress() != null){
            existingProfile.setAddress(profileUpdate.getAddress());
        }
    }
}
