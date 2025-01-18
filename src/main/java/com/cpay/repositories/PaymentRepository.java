package com.cpay.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.cpay.entities.Payment;

public interface PaymentRepository extends CrudRepository<Payment, Long> {

	Optional<Payment> findByCreditCardCardNumber(String cardNumber);
}
