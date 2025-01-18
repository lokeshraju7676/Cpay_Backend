package com.cpay.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import com.cpay.entities.UserEntity;
import com.cpay.repositories.UserRepository;
import com.cpay.security.payload.response.MessageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserController userController;

	private UserEntity user;

	@BeforeEach
	public void setUp() {
		user = new UserEntity();
		user.setId(1L);
		user.setUsername("testUser");
		user.setEmail("testUser@example.com");
		user.setMobile("1234567890");
		user.setAddress("123 Test St");
		user.setGender("Male");
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGetUserById() {

		when(userRepository.findById(1L)).thenReturn(Optional.of(user));

		ResponseEntity<?> response = userController.getUserById(1L);

		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());

		UserEntity responseUser = (UserEntity) response.getBody();
		assertEquals("testUser", responseUser.getUsername());

		verify(userRepository, times(1)).findById(1L);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGetUserByIdNotFound() {

		when(userRepository.findById(1L)).thenReturn(Optional.empty());

		ResponseEntity<?> response = userController.getUserById(1L);

		assertNotNull(response);
		assertEquals(400, response.getStatusCodeValue());

		MessageResponse messageResponse = (MessageResponse) response.getBody();
		assertNotNull(messageResponse);
		assertEquals("User not found", messageResponse.getMessage());

		verify(userRepository, times(1)).findById(1L);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testUpdateUser() {

		UserEntity updatedUser = new UserEntity();
		updatedUser.setUsername("updatedUser");
		updatedUser.setEmail("updatedUser@example.com");
		updatedUser.setMobile("9876543210");
		updatedUser.setAddress("456 Updated St");
		updatedUser.setGender("Female");

		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		when(userRepository.save(any(UserEntity.class))).thenReturn(updatedUser);

		ResponseEntity<?> response = userController.updateUser(1L, updatedUser);

		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());

		MessageResponse messageResponse = (MessageResponse) response.getBody();
		assertNotNull(messageResponse);
		assertEquals("User updated successfully!", messageResponse.getMessage());

		verify(userRepository, times(1)).findById(1L);
		verify(userRepository, times(1)).save(any(UserEntity.class));
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testUpdateUserNotFound() {

		UserEntity updatedUser = new UserEntity();
		updatedUser.setUsername("updatedUser");
		updatedUser.setEmail("updatedUser@example.com");
		updatedUser.setMobile("9876543210");
		updatedUser.setAddress("456 Updated St");
		updatedUser.setGender("Female");

		when(userRepository.findById(1L)).thenReturn(Optional.empty());

		ResponseEntity<?> response = userController.updateUser(1L, updatedUser);

		assertNotNull(response);
		assertEquals(400, response.getStatusCodeValue());

		MessageResponse messageResponse = (MessageResponse) response.getBody();
		assertNotNull(messageResponse);
		assertEquals("User not found", messageResponse.getMessage());

		verify(userRepository, times(1)).findById(1L);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testDeleteUser() {

		when(userRepository.existsById(1L)).thenReturn(true);

		ResponseEntity<?> response = userController.deleteUser(1L);

		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());

		MessageResponse messageResponse = (MessageResponse) response.getBody();
		assertNotNull(messageResponse);
		assertEquals("User deleted successfully!", messageResponse.getMessage());

		verify(userRepository, times(1)).existsById(1L);
		verify(userRepository, times(1)).deleteById(1L);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testDeleteUserNotFound() {

		when(userRepository.existsById(1L)).thenReturn(false);

		ResponseEntity<?> response = userController.deleteUser(1L);

		assertNotNull(response);
		assertEquals(400, response.getStatusCodeValue());

		MessageResponse messageResponse = (MessageResponse) response.getBody();
		assertNotNull(messageResponse);
		assertEquals("User not found", messageResponse.getMessage());

		verify(userRepository, times(1)).existsById(1L);
	}
}
