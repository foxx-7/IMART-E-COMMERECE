package com.api.user.controller;

import com.api.user.dto.UserRequest;
import com.api.user.dto.UserResponse;
import com.api.user.model.User;
import com.api.user.service.UserService;
import com.api.user.utility.UserMapper;
import com.api.user.utility.UserResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper = new UserMapper();
    private final UserResponseMapper userResponseMapper= new UserResponseMapper();

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request){
        return new ResponseEntity<UserResponse>(userResponseMapper.mapToUserResponse(userService.addUser(userMapper.mapToUser(request))), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> fetchUserById(@PathVariable String id){
        return userService.getUserById(id)
                .map(userResponseMapper::mapToUserResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<User>> fetchAllUsers(){
        return ResponseEntity.ok(userService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id){
        Optional<User> userOpt = userService.getUserById(id);
        if(userOpt.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        User user = userOpt.get();
        userService.removeUserById(String.valueOf(user.getId()));
        return ResponseEntity.ok("user deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable String id, @RequestBody UserRequest request){
        if(!userService.updateProfile(id, UserMapper.mapToUser(request))){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user does not exist");
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("user updated successfully");
    }

}
