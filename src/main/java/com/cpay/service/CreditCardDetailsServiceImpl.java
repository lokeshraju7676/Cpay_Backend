package com.cpay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpay.entities.CreditCardDetails;
import com.cpay.repositories.CreditCardDetailsRepository;

@Service
public class CreditCardDetailsServiceImpl implements CreditCardDetailsService {

	@Autowired
	private CreditCardDetailsRepository creditCardDetailsRepository;

	@Override
	public CreditCardDetails createCardDetails(CreditCardDetails creditCardDetails) {
		return creditCardDetailsRepository.save(creditCardDetails);
	}

	@Override
	public CreditCardDetails getCardDetailsByCardNumber(String cardNumber) {
		return creditCardDetailsRepository.findByCardNumber(cardNumber);
	}

	/*
	 * @Override public List<CreditCardDetails> getAllCardDetails() { // Fetch all
	 * card details from the repository return (List<CreditCardDetails>)
	 * creditCardDetailsRepository.findAll(); }
	 */

}
