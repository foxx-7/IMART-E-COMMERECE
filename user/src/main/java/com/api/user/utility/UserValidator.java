package com.api.user.utility;

import com.api.user.Exception.FailedOperationException;
import com.api.user.model.User;
import com.api.user.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
public class UserValidator {
    private final UserRepo userRepo;

    public void validateUser(User user){
        if(userRepo.existsByEmail(user.getEmail())){
            throw new FailedOperationException("user with email "+user.getEmail()+" already exists!");
        }
        if(userRepo.existsByUserName(user.getUserName())){
            throw new FailedOperationException("username "+user.getUserName()+" already in use");
        }
        if(userRepo.existsByContact(user.getContact())){
            throw new FailedOperationException("contact "+user.getContact()+" already in use");
        }
    }
}