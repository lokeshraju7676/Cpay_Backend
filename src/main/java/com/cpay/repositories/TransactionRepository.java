package com.cpay.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cpay.entities.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

	List<Transaction> findByCreditCardCardNumber(String cardNumber);
}
