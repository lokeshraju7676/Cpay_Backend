package com.cpay.service;

import com.cpay.entities.Payment;

public interface PaymentService {

    // Process a payment and create transaction for it
    Payment processPayment(Payment payment);

    // Get payment by card number
    Payment getPaymentByCardNumber(String cardNumber);
}
