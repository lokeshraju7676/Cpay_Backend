package com.cpay.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpay.entities.Transaction;
import com.cpay.repositories.TransactionRepository;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Transaction recordTransaction(Transaction transaction) {
        logger.info("Recording transaction for card number: {}", transaction.getCreditCard().getCardNumber());

        // Save the transaction and log the operation
        Transaction savedTransaction = transactionRepository.save(transaction);
        logger.info("Transaction recorded successfully with ID: {}", savedTransaction.getId());

        return savedTransaction;
    }

    @Override
    public List<Transaction> getTransactionsByCardNumber(String cardNumber) {
        logger.info("Fetching transactions for card number: {}", cardNumber);

        // Fetch the transactions associated with the card number
        List<Transaction> transactions = transactionRepository.findByCreditCardCardNumber(cardNumber);
        
        if (transactions.isEmpty()) {
            logger.warn("No transactions found for card number: {}", cardNumber);
        } else {
            logger.info("Found {} transactions for card number: {}", transactions.size(), cardNumber);
        }

        return transactions;
    }
}
