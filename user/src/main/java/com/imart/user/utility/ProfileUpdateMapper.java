package com.imart.user.utility;

import com.imart.user.dto.AddressRequest;
import com.imart.user.exception.InvalidUpdateFieldException;
import com.imart.user.model.UserProfile;
import com.imart.user.repository.ProfileRepository;
import com.imart.user.service.AddressService;
import com.imart.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ProfileUpdateMapper {
    private final AddressService addressService;
    private final ProfileRepository profileRepository;
    public void updateProfileData(UserProfile existingProfile, Map<String, Object> profileUpdate){
       profileUpdate.forEach((key, value) -> {
           switch (key){
               case "firstName" -> existingProfile.setFirstName((String) value);
               case "lastName" -> existingProfile.setLastName((String)value);
               case "email" -> {
                   final String email = (String) profileUpdate.get("email");
                   if(profileRepository.existsByEmail(email)){
                       throw new DataIntegrityViolationException("email:" + email+ " already in use");
                   }
                   existingProfile.setEmail((String)value);
               }
               case "contact" -> {
                   final String contact = (String)profileUpdate.get("contact");
                   if(profileRepository.existsByContact(contact)){
                       throw new DataIntegrityViolationException("contact: "+contact+" already in use");
                   }
                   existingProfile.setContact((String) value);
               }
               case "address" -> addressService.addAddress((AddressRequest) value);
               default -> throw new InvalidUpdateFieldException("invalid update field provided:"+key);
           }
       });
    }
}
