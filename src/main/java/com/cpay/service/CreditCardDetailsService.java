package com.cpay.service;

import com.cpay.entities.CreditCardDetails;

public interface CreditCardDetailsService {

    // Create new CreditCardDetails
    CreditCardDetails createCardDetails(CreditCardDetails creditCardDetails);

    // Get CreditCardDetails by card number
    CreditCardDetails getCardDetailsByCardNumber(String cardNumber);
}
