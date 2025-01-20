package com.cpay.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpay.entities.CreditCardDetails;
import com.cpay.service.CreditCardDetailsService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/carddetails")
public class CreditCardDetailsController {

    private static final Logger logger = LoggerFactory.getLogger(CreditCardDetailsController.class);

    @Autowired
    private CreditCardDetailsService creditCardDetailsService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<CreditCardDetails> createCardDetails(@RequestBody CreditCardDetails creditCardDetails) {
        logger.info("Received request to create card details for card number: {}", creditCardDetails.getCardNumber());
        
        CreditCardDetails savedCardDetails = creditCardDetailsService.createCardDetails(creditCardDetails);
        
        if (savedCardDetails != null) {
            logger.info("Successfully created card details for card number: {}", creditCardDetails.getCardNumber());
            return ResponseEntity.ok(savedCardDetails);
        } else {
            logger.error("Failed to create card details for card number: {}", creditCardDetails.getCardNumber());
            return ResponseEntity.status(500).body(null);
        }
    }

    //@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    @GetMapping("/{cardNumber}")
    public ResponseEntity<CreditCardDetails> getCardDetailsByCardNumber(@PathVariable String cardNumber) {
        logger.info("Received request to fetch card details for card number: {}", cardNumber);
        
        CreditCardDetails cardDetails = creditCardDetailsService.getCardDetailsByCardNumber(cardNumber);
        
        if (cardDetails != null) {
            logger.info("Successfully retrieved card details for card number: {}", cardNumber);
            return ResponseEntity.ok(cardDetails);
        } else {
            logger.warn("Card details not found for card number: {}", cardNumber);
            return ResponseEntity.status(404).body(null);
        }
    }

    /*
    // New endpoint to fetch all card details

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/all")
    public ResponseEntity<List<CreditCardDetails>> getAllCardDetails() {
        logger.info("Received request to fetch all card details.");
        List<CreditCardDetails> cardDetailsList = creditCardDetailsService.getAllCardDetails();
        logger.info("Successfully retrieved {} card details.", cardDetailsList.size());
        return ResponseEntity.ok(cardDetailsList);
    }
    */
}
