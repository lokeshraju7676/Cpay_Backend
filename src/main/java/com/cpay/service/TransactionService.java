package com.cpay.service;

import com.cpay.entities.Transaction;
import java.util.List;

public interface TransactionService {

	Transaction recordTransaction(Transaction transaction);

	List<Transaction> getTransactionsByCardNumber(String cardNumber);
}
