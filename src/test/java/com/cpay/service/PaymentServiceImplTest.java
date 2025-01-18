package com.cpay.service;

import com.cpay.entities.Payment;
import com.cpay.entities.Transaction;
import com.cpay.entities.CreditCardDetails;
import com.cpay.entities.ERole.EPaymentStatus;
import com.cpay.repositories.PaymentRepository;
import com.cpay.repositories.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class PaymentServiceImplTest {

	@Mock
	private PaymentRepository paymentRepository;

	@Mock
	private TransactionRepository transactionRepository;

	@InjectMocks
	private PaymentServiceImpl paymentService;

	private Payment payment;

	@BeforeEach
	void setUp() {

		CreditCardDetails creditCardDetails = new CreditCardDetails();
		creditCardDetails.setCardNumber("1234567890123456");

		payment = new Payment();
		payment.setAmount(100.0);
		payment.setPaymentDate(LocalDate.now());
		payment.setPaymentStatus(EPaymentStatus.COMPLETED);
		payment.setCreditCard(creditCardDetails);
	}

	@Test
	void testProcessPayment_Success() {

		Mockito.when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

		Transaction transaction = new Transaction();
		Mockito.when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

		Payment processedPayment = paymentService.processPayment(payment);

		assertNotNull(processedPayment);
		assertEquals(100.0, processedPayment.getAmount());
		assertEquals(EPaymentStatus.COMPLETED, processedPayment.getPaymentStatus());
		Mockito.verify(transactionRepository, Mockito.times(1)).save(any(Transaction.class));
	}

	@Test
	void testGetPaymentByCardNumber_Found() {

		Mockito.when(paymentRepository.findByCreditCardCardNumber("1234567890123456")).thenReturn(Optional.of(payment));

		Payment foundPayment = paymentService.getPaymentByCardNumber("1234567890123456");

		// Assertions
		assertNotNull(foundPayment);
		assertEquals("1234567890123456", foundPayment.getCreditCard().getCardNumber());
	}

	@Test
	void testGetPaymentByCardNumber_NotFound() {

		Mockito.when(paymentRepository.findByCreditCardCardNumber("nonexistentcardnumber"))
				.thenReturn(Optional.empty());

		RuntimeException exception = assertThrows(RuntimeException.class,
				() -> paymentService.getPaymentByCardNumber("nonexistentcardnumber"));

		// Assertions
		assertEquals("Payment not found for card number: nonexistentcardnumber", exception.getMessage());
	}

	@Test
	void testCreateTransactionForPayment() {

		Mockito.when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

		Transaction transaction = new Transaction();
		Mockito.when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

		Payment processedPayment = paymentService.processPayment(payment);

		assertNotNull(processedPayment, "Processed payment should not be null");
		assertEquals(100.0, processedPayment.getAmount(), "The payment amount should match the expected value");
		assertEquals(EPaymentStatus.COMPLETED, processedPayment.getPaymentStatus(),
				"The payment status should be 'COMPLETED'");

		Mockito.verify(transactionRepository, Mockito.times(1)).save(any(Transaction.class));
	}

}
