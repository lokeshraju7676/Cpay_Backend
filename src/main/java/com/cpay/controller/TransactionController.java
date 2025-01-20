package com.cpay.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cpay.entities.Transaction;
import com.cpay.service.TransactionService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/transactions")
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/card/{cardNumber}")
    public ResponseEntity<Iterable<Transaction>> getTransactionsByCardNumber(@PathVariable String cardNumber) {
        // Log the incoming request for transactions based on card number
        logger.info("Received request to fetch transactions for card number: {}", cardNumber);

        Iterable<Transaction> transactions = transactionService.getTransactionsByCardNumber(cardNumber);

        // Log the result after fetching transactions
        if (transactions.iterator().hasNext()) {
            logger.info("Successfully retrieved transactions for card number: {}", cardNumber);
        } else {
            logger.warn("No transactions found for card number: {}", cardNumber);
        }

        return ResponseEntity.ok(transactions);
    }
}
