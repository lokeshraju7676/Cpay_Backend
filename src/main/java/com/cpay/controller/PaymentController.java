package com.cpay.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpay.entities.Payment;
import com.cpay.service.PaymentService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/payments")
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaymentService paymentService;

    //@PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/process")
    public ResponseEntity<Payment> processPayment(@RequestBody Payment payment) {
        // Log the incoming payment request
        logger.info("Received payment processing request for card number: {}");

        Payment processedPayment = paymentService.processPayment(payment);

        // Log the successful payment processing
        logger.info("Successfully processed payment for card number: {}");

        return ResponseEntity.ok(processedPayment);
    }

    @GetMapping("/card/{cardNumber}")
    public ResponseEntity<Payment> getPaymentByCardNumber(@PathVariable String cardNumber) {
        // Log the incoming request to fetch payment details
        logger.info("Received request to fetch payment details for card number: {}", cardNumber);

        Payment payment = paymentService.getPaymentByCardNumber(cardNumber);

        // Log the result of fetching payment details
        if (payment != null) {
            logger.info("Successfully retrieved payment details for card number: {}", cardNumber);
        } else {
            logger.warn("No payment found for card number: {}", cardNumber);
        }

        return ResponseEntity.ok(payment);
    }
}
