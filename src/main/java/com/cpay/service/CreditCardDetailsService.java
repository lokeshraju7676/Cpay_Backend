package com.cpay.service;

import com.cpay.entities.CreditCardDetails;

public interface CreditCardDetailsService {

	CreditCardDetails createCardDetails(CreditCardDetails creditCardDetails);

	CreditCardDetails getCardDetailsByCardNumber(String cardNumber);
}
