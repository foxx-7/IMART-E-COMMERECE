package com.imart.user.service;

import com.imart.user.dto.AddressRequest;
import com.imart.user.exception.NotFoundException;
import com.imart.user.dto.ProfileResponse;
import com.imart.user.model.Address;
import com.imart.user.model.UserProfile;
import com.imart.user.repository.ProfileRepository;
import com.imart.user.utility.ProfileResponseMapper;
import com.imart.user.utility.ProfileUpdateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final ProfileRepository profileRepository;
    private final ProfileUpdateMapper profileUpdateMapper;
    private final AddressService addressService;

    @Transactional
    public UserProfile createProfile(UserProfile profile) throws DataIntegrityViolationException{
        return profileRepository.save(profile);
    }

    @Transactional
    public  UserProfile getProfileByUserId(Long userId) {
        Optional<UserProfile> profileOpt = profileRepository.findById(userId);
        if (profileOpt.isEmpty()){
            throw new NotFoundException("user profile not found");
        }
        return profileOpt.get();
    }

    public List<UserProfile> getAll() {
        return profileRepository.findAll();
    }

    @Transactional
    public UserProfile updateProfile(Long userId, Map<String, Object> profileUpdate){

        Optional<UserProfile> profileOptional = profileRepository.findById(userId);
        if(profileOptional.isEmpty()){
            throw new NotFoundException("user not found");
        }
        UserProfile existingProfile = profileOptional.get();
        profileUpdateMapper.updateProfileData(existingProfile, profileUpdate);
       profileRepository.save(existingProfile);
       return existingProfile;
    }

    @Transactional
    public  void removeProfileById(Long userId) {
        Optional<UserProfile> userProfileOptional = profileRepository.findById(userId);
        if(userProfileOptional.isEmpty()){
            throw new NotFoundException("user: " + userId + " does not exist");
        }
        profileRepository.delete(userProfileOptional.get());
    }

    @Transactional
    public Address addAddress(AddressRequest addressRequest){
        return addressService.addAddress(addressRequest);
    }
}
