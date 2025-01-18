package com.cpay.controller;

import com.cpay.entities.UserEntity;
import com.cpay.repositories.UserRepository;
import com.cpay.security.payload.response.MessageResponse;
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

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/all")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<UserEntity>> getAllUsers() {
		List<UserEntity> users = (List<UserEntity>) userRepository.findAll();
		return ResponseEntity.ok(users);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getUserById(@PathVariable Long id) {
		Optional<UserEntity> user = userRepository.findById(id);
		if (user.isPresent()) {
			return ResponseEntity.ok(user.get());
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("User not found"));
		}
	}

	@GetMapping("/username/{username}")
	public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
		Optional<UserEntity> user = userRepository.findByUsername(username);
		if (user.isPresent()) {
			return ResponseEntity.ok(user.get());
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("User not found"));
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserEntity userDetails) {
		Optional<UserEntity> existingUser = userRepository.findById(id);

		if (existingUser.isPresent()) {
			UserEntity user = existingUser.get();
			user.setUsername(userDetails.getUsername());
			user.setEmail(userDetails.getEmail());
			user.setMobile(userDetails.getMobile());
			user.setAddress(userDetails.getAddress());
			user.setGender(userDetails.getGender());

			userRepository.save(user);
			return ResponseEntity.ok(new MessageResponse("User updated successfully!"));
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("User not found"));
		}
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')") 
	public ResponseEntity<?> deleteUser(@PathVariable Long id) {
		if (userRepository.existsById(id)) {
			userRepository.deleteById(id);
			return ResponseEntity.ok(new MessageResponse("User deleted successfully!"));
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("User not found"));
		}
	}
}
