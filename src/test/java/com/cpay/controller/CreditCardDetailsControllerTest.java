package com.cpay.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.cpay.entities.CreditCardDetails;
import com.cpay.service.CreditCardDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class CreditCardDetailsControllerTest {

	@Mock
	private CreditCardDetailsService creditCardDetailsService;

	@InjectMocks
	private CreditCardDetailsController creditCardDetailsController;

	private CreditCardDetails creditCardDetails;

	@BeforeEach
	void setUp() {
		creditCardDetails = new CreditCardDetails();
		creditCardDetails.setCardNumber("1234567812345678");
		creditCardDetails.setExpirationDate("12/23");
		creditCardDetails.setCvv("123");
		creditCardDetails.setCardholderName("John Doe");
	}

	@SuppressWarnings("deprecation")
	@Test
	void testCreateCardDetails() {

		when(creditCardDetailsService.createCardDetails(any(CreditCardDetails.class))).thenReturn(creditCardDetails);

		ResponseEntity<CreditCardDetails> response = creditCardDetailsController.createCardDetails(creditCardDetails);

		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());

		CreditCardDetails savedCardDetails = response.getBody();
		assertNotNull(savedCardDetails);
		assertEquals("1234567812345678", savedCardDetails.getCardNumber());

		verify(creditCardDetailsService, times(1)).createCardDetails(any(CreditCardDetails.class));
	}

	@SuppressWarnings("deprecation")
	@Test
	void testGetCardDetailsByCardNumber() {

		when(creditCardDetailsService.getCardDetailsByCardNumber("1234567812345678")).thenReturn(creditCardDetails);

		ResponseEntity<CreditCardDetails> response = creditCardDetailsController
				.getCardDetailsByCardNumber("1234567812345678");

		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());

		CreditCardDetails returnedCardDetails = response.getBody();
		assertNotNull(returnedCardDetails);
		assertEquals("1234567812345678", returnedCardDetails.getCardNumber());

		verify(creditCardDetailsService, times(1)).getCardDetailsByCardNumber("1234567812345678");
	}

	@SuppressWarnings("deprecation")
	@Test
	void testGetCardDetailsByCardNumberNotFound() {

		when(creditCardDetailsService.getCardDetailsByCardNumber("1234567812345678")).thenReturn(null);

		ResponseEntity<CreditCardDetails> response = creditCardDetailsController
				.getCardDetailsByCardNumber("1234567812345678");

		assertNotNull(response);
		assertEquals(404, response.getStatusCodeValue());

		verify(creditCardDetailsService, times(1)).getCardDetailsByCardNumber("1234567812345678");
	}
}
