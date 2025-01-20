package com.cpay.controller;

import com.cpay.entities.UserEntity;
import com.cpay.repositories.UserRepository;
import com.cpay.security.payload.response.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        logger.info("Fetching all users.");
        List<UserEntity> users = (List<UserEntity>) userRepository.findAll();
        logger.info("Successfully retrieved {} users.", users.size());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        logger.info("Fetching user by ID: {}", id);
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isPresent()) {
            logger.info("User with ID {} found.", id);
            return ResponseEntity.ok(user.get());
        } else {
            logger.warn("User with ID {} not found.", id);
            return ResponseEntity.badRequest().body(new MessageResponse("User not found"));
        }
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        logger.info("Fetching user by username: {}", username);
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            logger.info("User with username {} found.", username);
            return ResponseEntity.ok(user.get());
        } else {
            logger.warn("User with username {} not found.", username);
            return ResponseEntity.badRequest().body(new MessageResponse("User not found"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserEntity userDetails) {
        logger.info("Updating user with ID: {}", id);
        Optional<UserEntity> existingUser = userRepository.findById(id);

        if (existingUser.isPresent()) {
            UserEntity user = existingUser.get();
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());
            user.setMobile(userDetails.getMobile());
            user.setAddress(userDetails.getAddress());
            user.setGender(userDetails.getGender());

            userRepository.save(user);
            logger.info("User with ID {} updated successfully.", id);
            return ResponseEntity.ok(new MessageResponse("User updated successfully!"));
        } else {
            logger.warn("User with ID {} not found for update.", id);
            return ResponseEntity.badRequest().body(new MessageResponse("User not found"));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        logger.info("Deleting user with ID: {}", id);
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            logger.info("User with ID {} deleted successfully.", id);
            return ResponseEntity.ok(new MessageResponse("User deleted successfully!"));
        } else {
            logger.warn("User with ID {} not found for deletion.", id);
            return ResponseEntity.badRequest().body(new MessageResponse("User not found"));
        }
    }
}
