package com.cpay.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.cpay.entities.UserEntity;
import com.cpay.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImplTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserDetailsServiceImpl userDetailsService;

	private UserEntity userEntity;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		userEntity = new UserEntity();
		userEntity.setId(1L);
		userEntity.setUsername("testuser");
		userEntity.setPassword("password123");
	}

	@Test
	void testLoadUserByUsername_Success() {

		when(userRepository.findByUsername("testuser")).thenReturn(java.util.Optional.of(userEntity));

		UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername("testuser");

		assertNotNull(userDetails);
		assertEquals("testuser", userDetails.getUsername());
		assertEquals("password123", userDetails.getPassword());
	}

	@Test
	void testLoadUserByUsername_UserNotFound() {

		when(userRepository.findByUsername("nonexistentuser")).thenReturn(java.util.Optional.empty());

		assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("nonexistentuser"));
	}

	@Test
	void testLoadUserByUsername_WithNullUsername() {

		assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(null));
	}
}
