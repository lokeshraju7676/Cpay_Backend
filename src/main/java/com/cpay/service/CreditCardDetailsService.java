package com.cpay.service;

import java.util.List;

import com.cpay.entities.CreditCardDetails;

public interface CreditCardDetailsService {

	CreditCardDetails createCardDetails(CreditCardDetails creditCardDetails);

	CreditCardDetails getCardDetailsByCardNumber(String cardNumber);
	
	
	
	//List<CreditCardDetails> getAllCardDetails(); 
}
