package com.api.user.repository;

import com.api.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long>{
    boolean existsByEmail(String email);
    boolean existsByContact(String contact);
    boolean existsByUserName(String userName);
}