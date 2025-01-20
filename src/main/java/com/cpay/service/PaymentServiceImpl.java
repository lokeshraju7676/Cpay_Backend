package com.cpay.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpay.entities.Payment;
import com.cpay.entities.Transaction;
import com.cpay.repositories.PaymentRepository;
import com.cpay.repositories.TransactionRepository;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Payment processPayment(Payment payment) {
        logger.info("Processing payment for card number: {}", payment.getCreditCard().getCardNumber());

        // Save the payment and log the operation
        Payment savedPayment = paymentRepository.save(payment);
        logger.info("Payment processed successfully with ID: {}", savedPayment.getId());

        // Create the transaction for the payment
        createTransactionForPayment(savedPayment);

        return savedPayment;
    }

    private void createTransactionForPayment(Payment payment) {
        logger.info("Creating transaction for payment ID: {}", payment.getId());

        // Create a new transaction associated with the payment
        Transaction transaction = new Transaction();
        transaction.setAmount(payment.getAmount());
        transaction.setTransactionType("Debit"); // Debit for payment
        transaction.setTransactionDate(payment.getPaymentDate());
        transaction.setTransactionStatus(payment.getPaymentStatus());
        transaction.setCreditCard(payment.getCreditCard()); // Link to the CreditCardDetails

        // Save the transaction and log the operation
        transactionRepository.save(transaction);
        logger.info("Transaction created with ID: {}", transaction.getId());
    }

    @Override
    public Payment getPaymentByCardNumber(String cardNumber) {
        logger.info("Fetching payment for card number: {}", cardNumber);

        // Retrieve the payment associated with the card number
        Payment payment = paymentRepository.findByCreditCardCardNumber(cardNumber)
                .orElseThrow(() -> {
                    logger.error("Payment not found for card number: {}", cardNumber);
                    return new RuntimeException("Payment not found for card number: " + cardNumber);
                });

        logger.info("Found payment with ID: {}", payment.getId());
        return payment;
    }
}
