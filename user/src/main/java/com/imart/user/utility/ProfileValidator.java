package com.imart.user.utility;

import com.imart.user.exception.AlreadyInUseException;
import com.imart.user.model.UserProfile;
import com.imart.user.repository.ProfileRepository;
import org.springframework.stereotype.Component;

@Component
public class ProfileValidator {

    public void validateProfile(ProfileRepository profileRepository,UserProfile profile){
        if(profileRepository.existsByEmail(profile.getEmail())){
            throw new AlreadyInUseException("email already in use");
        }
        if (profileRepository.existsByContact(profile.getContact())){
            throw new AlreadyInUseException("contact is already in use");
        }
    }

    public void validateProfileUpdate(ProfileRepository profileRepository, UserProfile update){
        if(update.getEmail() != null){
            if(profileRepository.existsByEmail(update.getEmail())){
                throw new AlreadyInUseException("provided email is already in use");
            }
        }
        if(update.getContact() != null){
            if(profileRepository.existsByContact(update.getContact())){
                throw new AlreadyInUseException("contact already in use");
            }
        }
    }
}