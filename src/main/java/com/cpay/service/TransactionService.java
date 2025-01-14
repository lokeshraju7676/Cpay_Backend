package com.cpay.service;

import com.cpay.entities.Transaction;

public interface TransactionService {
    Transaction recordTransaction(Transaction transaction);
    Iterable<Transaction> getTransactionsByCreditCardId(Long cardId);
}
