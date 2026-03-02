package com.api.user.service;


import com.api.user.model.User;
import com.api.user.repository.UserRepo;
import com.api.user.utility.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    public User addUser(User user) {
        UserValidator userValidator = new UserValidator(userRepo);
        userValidator.validateUser(user);
        return userRepo.save(user);
    }

    public Optional<User> getUserById(String id) {
        return userRepo.findById(Long.valueOf(id));
    }

    public List<User> getAll() {
        return userRepo.findAll();
    }
    public boolean updateProfile(String id, User updatedUser){
        Optional<User> userOptional = userRepo.findById(Long.valueOf(id));
        if(userOptional.isEmpty()){
            return false;
        }

       userRepo.save( updateUser(userOptional.get(), updatedUser));
        return true;
    }

    public void removeUserById(String id) {
        userRepo.deleteById(Long.valueOf(id));
    }

    private User updateUser(User existingUser, User updatedUser){
        existingUser.setUserName(updatedUser.getUserName());;
        existingUser.setFirstName(updatedUser.getFirstName());;
        existingUser.setLastName(updatedUser.getLastName());;
        existingUser.setContact(updatedUser.getContact());;
        existingUser.setEmail(updatedUser.getEmail());;
        existingUser.setAddress(existingUser.getAddress());;
        return existingUser;
    }
}
