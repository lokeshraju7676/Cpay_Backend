package com.cpay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpay.entities.Payment;
import com.cpay.exceptions.ResourceNotFoundException;
import com.cpay.repositories.PaymentRepository;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment processPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPaymentByApplicationId(Long applicationId) {
        return paymentRepository.findByCreditCardApplicationId(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found for Application ID: " + applicationId));
    }
}
