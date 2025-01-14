package com.cpay.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.cpay.entities.Payment;

public interface PaymentRepository extends CrudRepository<Payment, Long> {

    // Modify to return Optional<Payment>
    Optional<Payment> findByCreditCardApplicationId(Long applicationId);
}
