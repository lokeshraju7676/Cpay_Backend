package com.cpay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpay.entities.Transaction;
import com.cpay.exceptions.ResourceNotFoundException;
import com.cpay.repositories.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Transaction recordTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Iterable<Transaction> getTransactionsByCreditCardId(Long cardId) {
        Iterable<Transaction> transactions = transactionRepository.findByCreditCardId(cardId);
        if (!transactions.iterator().hasNext()) {
            throw new ResourceNotFoundException("No transactions found for Credit Card ID: " + cardId);
        }
        return transactions;
    }
}
