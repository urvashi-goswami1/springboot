package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.entity.UserProfile;
import com.example.demo.repository.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserRepository userRepository;

    public UserRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<User> createUser(
            @RequestBody User user) {

        User savedUser = userRepository.save(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedUser);
    }

    // GET ALL
    @GetMapping
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(
            @PathVariable int id) {

        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable int id,
            @RequestBody User newUser) {

        return userRepository.findById(id)
                .map(existingUser -> {

                    if (newUser.getUsername() != null) {
                        existingUser.setUsername(
                                newUser.getUsername());
                    }

                    if (newUser.getEmail() != null) {
                        existingUser.setEmail(
                                newUser.getEmail());
                    }

                    if (newUser.getUserProfile() != null) {

                        UserProfile newProfile =
                                newUser.getUserProfile();

                        UserProfile existingProfile =
                                existingUser.getUserProfile();

                        if (existingProfile == null) {
                            existingUser.setUserProfile(newProfile);
                        } else {
                            existingProfile.setAddress(
                                    newProfile.getAddress());

                            existingProfile.setPhone(
                                    newProfile.getPhone());

                            existingProfile.setDob(
                                    newProfile.getDob());
                        }
                    }

                    return ResponseEntity.ok(
                            userRepository.save(existingUser));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable int id) {

        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        userRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}