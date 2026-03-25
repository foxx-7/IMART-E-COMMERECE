package com.imart.user.controller;

import com.imart.user.dto.ProfileRequest;
import com.imart.user.dto.ProfileResponse;
import com.imart.user.model.UserProfile;
import com.imart.user.service.UserService;
import com.imart.user.utility.ProfileMapper;
import com.imart.user.utility.ProfileResponseMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/profiles")
    public ResponseEntity<ProfileResponse> createUserProfile(@RequestBody @Valid ProfileRequest request) {
        return new ResponseEntity<>(ProfileResponseMapper.mapToProfileResponse(
                userService.createProfile(ProfileMapper.mapToProfile(request))), HttpStatus.CREATED);
    }

    @GetMapping("/profiles")
    public ResponseEntity<ProfileResponse> fetchProfileById(@RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getProfileByUserId(userId));
    }

    @GetMapping("/admin/profiles")
    public ResponseEntity<List<UserProfile>> fetchAllProfiles() {
        return ResponseEntity.ok(userService.getAll());
    }

    @DeleteMapping("/profiles")
    public ResponseEntity<String> deleteProfile(@RequestHeader("X-User-Id") Long userId) {
        userService.removeProfileById(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("profile deleted successfully");
    }

    @PatchMapping("/profiles")
    public ResponseEntity<String> updateProfile(@RequestHeader("X-User-Id") Long userId, @RequestBody ProfileRequest request) {
        userService.updateProfile(userId, ProfileMapper.mapToProfile((request)));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("profile updated successfully");
    }
}

