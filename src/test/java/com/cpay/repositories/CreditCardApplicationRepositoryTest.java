package com.cpay.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cpay.entities.CreditCardApplication;
import com.cpay.entities.ERole.EApplicationStatus;

@SpringBootTest
public class CreditCardApplicationRepositoryTest {

	@Autowired
	private CreditCardApplicationRepository creditCardApplicationRepository;

	private CreditCardApplication creditCardApplication;

	@BeforeEach
	void setUp() {

		creditCardApplication = new CreditCardApplication();
		creditCardApplication.setApplicantName("John Doe");
		creditCardApplication.setApplicantEmail("johndoe@example.com");
		creditCardApplication.setMobileNumber("1234567890");
		creditCardApplication.setEmploymentStatus("Employed");
		creditCardApplication.setAnnualIncome(60000.0);
		creditCardApplication.setAddress("123 Main St, Anytown, USA");
		creditCardApplication.setApplicationDate(LocalDate.now());
		creditCardApplication.setApplicationStatus(EApplicationStatus.PENDING);
		creditCardApplication.setUsername("testuser");

		creditCardApplicationRepository.save(creditCardApplication);
	}

	@Test
	void testFindByUsername_Success() {

		Iterable<CreditCardApplication> applications = creditCardApplicationRepository.findByUsername("testuser");

		assertNotNull(applications);
		assertTrue(applications.iterator().hasNext());

		CreditCardApplication retrievedApplication = applications.iterator().next();
		assertEquals("testuser", retrievedApplication.getUsername());
		assertEquals(EApplicationStatus.PENDING, retrievedApplication.getApplicationStatus());
	}

	@Test
	void testFindByUsername_Empty() {

		Iterable<CreditCardApplication> applications = creditCardApplicationRepository
				.findByUsername("nonexistentuser");

		assertNotNull(applications);
		assertFalse(applications.iterator().hasNext());
	}

	@Test
	void testSaveAndFindById() {

		Optional<CreditCardApplication> savedApplication = creditCardApplicationRepository
				.findById(creditCardApplication.getId());

		assertTrue(savedApplication.isPresent());
		assertEquals("John Doe", savedApplication.get().getApplicantName());
		assertEquals("johndoe@example.com", savedApplication.get().getApplicantEmail());
		assertEquals(EApplicationStatus.PENDING, savedApplication.get().getApplicationStatus());
	}

	@Test
	void testFindByUsername_NullUsername() {

		Iterable<CreditCardApplication> applications = creditCardApplicationRepository.findByUsername(null);

		assertNotNull(applications);
		assertFalse(applications.iterator().hasNext());
	}

	@Test
	void testSaveApplication() {

		CreditCardApplication newApplication = new CreditCardApplication();
		newApplication.setApplicantName("Alice Smith");
		newApplication.setApplicantEmail("alice@example.com");
		newApplication.setMobileNumber("9876543210");
		newApplication.setEmploymentStatus("Self-employed");
		newApplication.setAnnualIncome(75000.0);
		newApplication.setAddress("456 Oak St, Anytown, USA");
		newApplication.setApplicationDate(LocalDate.now());
		newApplication.setApplicationStatus(EApplicationStatus.PENDING);
		newApplication.setUsername("aliceuser");

		CreditCardApplication savedApplication = creditCardApplicationRepository.save(newApplication);

		assertNotNull(savedApplication);
		assertEquals("Alice Smith", savedApplication.getApplicantName());
		assertEquals("alice@example.com", savedApplication.getApplicantEmail());
		assertEquals(EApplicationStatus.PENDING, savedApplication.getApplicationStatus());
	}

	@Test
	void testApplicationStatusUpdate() {

		creditCardApplication.setApplicationStatus(EApplicationStatus.APPROVED);
		CreditCardApplication updatedApplication = creditCardApplicationRepository.save(creditCardApplication);

		assertEquals(EApplicationStatus.APPROVED, updatedApplication.getApplicationStatus());
	}

	@Test
	void testDeleteById() {

		creditCardApplicationRepository.deleteById(creditCardApplication.getId());

		Optional<CreditCardApplication> deletedApplication = creditCardApplicationRepository
				.findById(creditCardApplication.getId());
		assertFalse(deletedApplication.isPresent());
	}
}
