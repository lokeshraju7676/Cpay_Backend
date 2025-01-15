package com.cpay.service;

import com.cpay.entities.Transaction;
import java.util.List;

public interface TransactionService {

    // Record a transaction
    Transaction recordTransaction(Transaction transaction);

    // Get transactions by card number
    List<Transaction> getTransactionsByCardNumber(String cardNumber);
}
