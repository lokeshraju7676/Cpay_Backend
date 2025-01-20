package com.cpay.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpay.entities.CreditCardDetails;
import com.cpay.repositories.CreditCardDetailsRepository;

@Service
public class CreditCardDetailsServiceImpl implements CreditCardDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CreditCardDetailsServiceImpl.class);

    @Autowired
    private CreditCardDetailsRepository creditCardDetailsRepository;

    @Override
    public CreditCardDetails createCardDetails(CreditCardDetails creditCardDetails) {
        logger.info("Creating new credit card details for card number: {}", creditCardDetails.getCardNumber());

        // Save and return the created credit card details
        CreditCardDetails savedCardDetails = creditCardDetailsRepository.save(creditCardDetails);
        logger.info("Credit card details saved for card number: {}", savedCardDetails.getCardNumber());

        return savedCardDetails;
    }

    @Override
    public CreditCardDetails getCardDetailsByCardNumber(String cardNumber) {
        logger.info("Fetching credit card details for card number: {}", cardNumber);

        CreditCardDetails cardDetails = creditCardDetailsRepository.findByCardNumber(cardNumber);

        if (cardDetails != null) {
            logger.info("Found credit card details for card number: {}", cardNumber);
        } else {
            logger.warn("No credit card details found for card number: {}", cardNumber);
        }

        return cardDetails;
    }

    // Uncomment and implement this method if required
    /*
    @Override
    public List<CreditCardDetails> getAllCardDetails() {
        logger.info("Fetching all credit card details");

        List<CreditCardDetails> allCardDetails = (List<CreditCardDetails>) creditCardDetailsRepository.findAll();
        
        if (allCardDetails.isEmpty()) {
            logger.warn("No credit card details found");
        } else {
            logger.info("Retrieved {} credit card details", allCardDetails.size());
        }

        return allCardDetails;
    }
    */
}
