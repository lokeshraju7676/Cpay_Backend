package com.cpay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpay.entities.Transaction;
import com.cpay.service.TransactionService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

	/*
	 * @PostMapping("/record") public ResponseEntity<Transaction>
	 * recordTransaction(@RequestBody Transaction transaction) { Transaction
	 * savedTransaction = transactionService.recordTransaction(transaction); return
	 * ResponseEntity.ok(savedTransaction); }
	 */

    @GetMapping("/card/{cardNumber}")
    public ResponseEntity<Iterable<Transaction>> getTransactionsByCardNumber(@PathVariable String cardNumber) {
        Iterable<Transaction> transactions = transactionService.getTransactionsByCardNumber(cardNumber);
        return ResponseEntity.ok(transactions);
    }
}
