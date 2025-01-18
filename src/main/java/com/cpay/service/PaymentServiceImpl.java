package com.cpay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpay.entities.Payment;
import com.cpay.entities.Transaction;
import com.cpay.repositories.PaymentRepository;
import com.cpay.repositories.TransactionRepository;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public Payment processPayment(Payment payment) {
		// Save payment
		Payment savedPayment = paymentRepository.save(payment);

		// Create corresponding transaction
		createTransactionForPayment(savedPayment);

		return savedPayment;
	}

	private void createTransactionForPayment(Payment payment) {
		// Create transaction for the payment
		Transaction transaction = new Transaction();
		transaction.setAmount(payment.getAmount());
		transaction.setTransactionType("Debit"); // Debit for payment
		transaction.setTransactionDate(payment.getPaymentDate());
		transaction.setTransactionStatus(payment.getPaymentStatus());
		transaction.setCreditCard(payment.getCreditCard()); // Link to the CreditCardDetails

		transactionRepository.save(transaction);
	}

	@Override
	public Payment getPaymentByCardNumber(String cardNumber) {
		return paymentRepository.findByCreditCardCardNumber(cardNumber)
				.orElseThrow(() -> new RuntimeException("Payment not found for card number: " + cardNumber));
	}
}
