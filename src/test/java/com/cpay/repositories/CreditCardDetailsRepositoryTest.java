package com.cpay.repositories;

import java.util.Base64;
import com.cpay.entities.CreditCardDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CreditCardDetailsRepositoryTest {

	@Autowired
	private CreditCardDetailsRepository creditCardDetailsRepository;

	private CreditCardDetails creditCardDetails;

	@BeforeEach
	void setUp() {

		String uniqueCardNumber = "1234567890123456" + System.currentTimeMillis();

		creditCardDetails = new CreditCardDetails();
		creditCardDetails.setCardNumber(uniqueCardNumber);
		creditCardDetails.setExpirationDate("12/25");
		creditCardDetails.setCvv("123");
		creditCardDetails.setCardholderName("John Doe");

		creditCardDetailsRepository.save(creditCardDetails);
	}

	@Test
	void testFindByCardNumber_Success() {

		CreditCardDetails retrievedCard = creditCardDetailsRepository
				.findByCardNumber(creditCardDetails.getCardNumber());

		assertNotNull(retrievedCard);
		assertEquals(creditCardDetails.getCardNumber(), retrievedCard.getCardNumber());
		assertEquals(creditCardDetails.getCardholderName(), retrievedCard.getCardholderName());
	}

	@Test
	void testFindByCardNumber_NotFound() {

		CreditCardDetails retrievedCard = creditCardDetailsRepository.findByCardNumber("0000000000000000");

		assertNull(retrievedCard);
	}

	@Test
	void testSaveAndFindById() {

		CreditCardDetails savedCard = creditCardDetailsRepository.findById(creditCardDetails.getId()).orElse(null);

		assertNotNull(savedCard);
		assertEquals(creditCardDetails.getCardNumber(), savedCard.getCardNumber());
	}

	@Test
	void testSaveAndUpdateCardDetails() {

		creditCardDetails.setExpirationDate("01/26");
		creditCardDetails.setCvv("321");

		CreditCardDetails updatedCard = creditCardDetailsRepository.save(creditCardDetails);

		String decodedCvv = new String(Base64.getDecoder().decode(updatedCard.getCvv()));

		assertNotNull(updatedCard);
		assertEquals("01/26", updatedCard.getExpirationDate());
		assertEquals("321", decodedCvv);
	}

	@Test
	void testDeleteById() {

		Long cardId = creditCardDetails.getId();
		creditCardDetailsRepository.deleteById(cardId);

		CreditCardDetails deletedCard = creditCardDetailsRepository.findById(cardId).orElse(null);
		assertNull(deletedCard);
	}

	@Test
	void testSaveNewCard() {

		CreditCardDetails newCard = new CreditCardDetails();
		newCard.setCardNumber("9876543210987654" + System.currentTimeMillis());
		newCard.setExpirationDate("11/23");
		newCard.setCvv("456");
		newCard.setCardholderName("Alice Smith");

		CreditCardDetails savedCard = creditCardDetailsRepository.save(newCard);

		assertNotNull(savedCard);
		assertEquals(newCard.getCardNumber(), savedCard.getCardNumber());
		assertEquals("Alice Smith", savedCard.getCardholderName());
	}
}
