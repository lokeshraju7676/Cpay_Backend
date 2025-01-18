package com.cpay.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.cpay.entities.CreditCardApplication;
import com.cpay.entities.ERole;
import com.cpay.service.CreditCardApplicationService;

@ExtendWith(MockitoExtension.class)
public class CreditCardApplicationControllerTest {

	@Mock
	private CreditCardApplicationService applicationService; 

	@InjectMocks
	private CreditCardApplicationController applicationController; 

	private CreditCardApplication application;

	@BeforeEach
	void setUp() {
		application = new CreditCardApplication();
		application.setApplicantName("John Doe");
		application.setApplicantEmail("john.doe@example.com");
		application.setMobileNumber("1234567890");
		application.setEmploymentStatus("Employed");
		application.setAnnualIncome(60000.0);
		application.setAddress("123 Main Street");
		application.setApplicationDate(LocalDate.of(2025, 1, 18));
		application.setApplicationStatus(ERole.EApplicationStatus.PENDING);
		application.setUsername("testUser");
	}

	@SuppressWarnings("deprecation")
	@Test
	void testApplyForCreditCard() {

		when(applicationService.applyForCreditCard(any(CreditCardApplication.class))).thenReturn(application);

		ResponseEntity<CreditCardApplication> response = applicationController.applyForCreditCard(application);

		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals("John Doe", response.getBody().getApplicantName());
		assertEquals("john.doe@example.com", response.getBody().getApplicantEmail());
		assertEquals("1234567890", response.getBody().getMobileNumber());
		assertEquals("Employed", response.getBody().getEmploymentStatus());
		assertEquals(60000.0, response.getBody().getAnnualIncome());
		assertEquals("123 Main Street", response.getBody().getAddress());
		assertEquals(LocalDate.of(2025, 1, 18), response.getBody().getApplicationDate());
		assertEquals(ERole.EApplicationStatus.PENDING, response.getBody().getApplicationStatus());
		assertEquals("testUser", response.getBody().getUsername());

		verify(applicationService, times(1)).applyForCreditCard(any(CreditCardApplication.class));
	}

	@SuppressWarnings("deprecation")
	@Test
	void testGetApplicationsByUserId() {

		when(applicationService.getApplicationsByUserId("testUser")).thenReturn(List.of(application));

		ResponseEntity<Iterable<CreditCardApplication>> response = applicationController
				.getApplicationsByUserId("testUser");

		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertTrue(((List<CreditCardApplication>) response.getBody()).size() > 0);
		assertEquals("John Doe", ((List<CreditCardApplication>) response.getBody()).get(0).getApplicantName());

		verify(applicationService, times(1)).getApplicationsByUserId("testUser");
	}

	@SuppressWarnings("deprecation")
	@Test
	void testGetApplicationByOrderId() {

		when(applicationService.getApplicationByOrderId("12345")).thenReturn(application);

		ResponseEntity<CreditCardApplication> response = applicationController.getApplicationByOrderId("12345");

		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals("John Doe", response.getBody().getApplicantName());
		assertEquals("john.doe@example.com", response.getBody().getApplicantEmail());

		verify(applicationService, times(1)).getApplicationByOrderId("12345");
	}

	@SuppressWarnings("deprecation")
	@Test
	void testGetApplicationByOrderIdNotFound() {

		when(applicationService.getApplicationByOrderId("99999")).thenReturn(null);

		ResponseEntity<CreditCardApplication> response = applicationController.getApplicationByOrderId("99999");

		assertNotNull(response);
		assertEquals(404, response.getStatusCodeValue());

		verify(applicationService, times(1)).getApplicationByOrderId("99999");
	}
}
