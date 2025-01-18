package com.cpay.repositories;

import org.springframework.data.repository.CrudRepository;

import com.cpay.entities.CreditCardDetails;

public interface CreditCardDetailsRepository extends CrudRepository<CreditCardDetails, Long> {

	CreditCardDetails findByCardNumber(String cardNumber);
}
