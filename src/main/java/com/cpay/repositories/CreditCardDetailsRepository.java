package com.cpay.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cpay.entities.CreditCardDetails;

public interface CreditCardDetailsRepository extends CrudRepository<CreditCardDetails, Long> {

	CreditCardDetails findByCardNumber(String cardNumber);
	/* public List<CreditCardDetails> getCardDetails(); */
}
