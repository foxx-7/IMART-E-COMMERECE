package com.imart.user.repository;

import com.imart.user.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<UserProfile,Long> {
    boolean existsByEmail(String email);
    boolean existsByContact(String contact);
    Optional<UserProfile> findByUserId(Long userId);
}