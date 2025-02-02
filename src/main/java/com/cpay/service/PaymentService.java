package com.cpay.service;

import com.cpay.entities.Payment;

public interface PaymentService {

	Payment processPayment(Payment payment);

	Payment getPaymentByCardNumber(String cardNumber);
}
