package com.imart.user.service;

import com.imart.user.exception.NotFoundException;
import com.imart.user.dto.ProfileResponse;
import com.imart.user.model.UserProfile;
import com.imart.user.repository.ProfileRepository;
import com.imart.user.utility.ProfileResponseMapper;
import com.imart.user.utility.ProfileUpdateMapper;
import com.imart.user.utility.ProfileValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final ProfileRepository profileRepository;
    private final ProfileUpdateMapper profileUpdateMapper;
    private final ProfileValidator profileValidator;

    public UserProfile createProfile(UserProfile profile) {
        profileValidator.validateProfile(profileRepository,profile);
        return profileRepository.save(profile);
    }

    public ProfileResponse  getProfileByUserId(Long userId) {
        Optional<UserProfile> profileOpt = profileRepository.findByUserId(userId);
        if (profileOpt.isEmpty()){
            throw new NotFoundException("user profile not found");
        }
        return ProfileResponseMapper.mapToProfileResponse(profileOpt.get());
    }

    public List<UserProfile> getAll() {
        return profileRepository.findAll();
    }

    public void  updateProfile(Long userId, UserProfile profileUpdate){
        Optional<UserProfile> profileOptional = profileRepository.findByUserId(userId);
        if(profileOptional.isEmpty()){
            throw new NotFoundException("user not found");
        }
        UserProfile existingProfile = profileOptional.get();
        profileValidator.validateProfileUpdate(profileRepository,profileUpdate);
        profileUpdateMapper.updateProfileData(existingProfile, profileUpdate);
       profileRepository.save(existingProfile);
    }

    public  void removeProfileById(Long userId) {
        if(!profileRepository.existsById(userId)) {
            throw new NotFoundException("profile not found");
        }
        profileRepository.deleteById(userId);
    }

}
