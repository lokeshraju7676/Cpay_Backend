package com.cpay.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cpay.entities.CreditCardDetails;
import com.cpay.repositories.CreditCardDetailsRepository;

@ExtendWith(MockitoExtension.class)
public class CreditCardDetailsServiceImplTest {

	@Mock
	private CreditCardDetailsRepository creditCardDetailsRepository;

	@InjectMocks
	private CreditCardDetailsServiceImpl creditCardDetailsService;

	private CreditCardDetails cardDetails;

	@BeforeEach
	public void setUp() {

		MockitoAnnotations.openMocks(this);

		cardDetails = new CreditCardDetails();
		cardDetails.setCardNumber("1234567890123456");
		cardDetails.setExpirationDate("12/25");
		cardDetails.setCvv("123");
		cardDetails.setCardholderName("John Doe");
	}

	@Test
	public void testCreateCardDetails() {

		when(creditCardDetailsRepository.save(any(CreditCardDetails.class))).thenReturn(cardDetails);

		CreditCardDetails savedCardDetails = creditCardDetailsService.createCardDetails(cardDetails);

		assertNotNull(savedCardDetails);
		assertEquals(cardDetails.getCardNumber(), savedCardDetails.getCardNumber());
		assertEquals(cardDetails.getExpirationDate(), savedCardDetails.getExpirationDate());
		assertEquals(cardDetails.getCvv(), savedCardDetails.getCvv());
		assertEquals(cardDetails.getCardholderName(), savedCardDetails.getCardholderName());

		verify(creditCardDetailsRepository, times(1)).save(any(CreditCardDetails.class));
	}

	@Test
	public void testGetCardDetailsByCardNumber() {

		when(creditCardDetailsRepository.findByCardNumber("1234567890123456")).thenReturn(cardDetails);

		CreditCardDetails fetchedCardDetails = creditCardDetailsService.getCardDetailsByCardNumber("1234567890123456");

		assertNotNull(fetchedCardDetails);
		assertEquals("1234567890123456", fetchedCardDetails.getCardNumber());
		assertEquals(cardDetails.getExpirationDate(), fetchedCardDetails.getExpirationDate());
		assertEquals(cardDetails.getCvv(), fetchedCardDetails.getCvv());
		assertEquals(cardDetails.getCardholderName(), fetchedCardDetails.getCardholderName());

		verify(creditCardDetailsRepository, times(1)).findByCardNumber("1234567890123456");
	}

	@Test
	public void testGetCardDetailsByCardNumber_NotFound() {

		when(creditCardDetailsRepository.findByCardNumber("nonexistentcardnumber")).thenReturn(null);

		CreditCardDetails fetchedCardDetails = creditCardDetailsService
				.getCardDetailsByCardNumber("nonexistentcardnumber");

		assertNull(fetchedCardDetails);

		verify(creditCardDetailsRepository, times(1)).findByCardNumber("nonexistentcardnumber");
	}
}
