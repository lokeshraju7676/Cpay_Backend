package com.cpay.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cpay.entities.CreditCardDetails;
import com.cpay.entities.ERole.EPaymentStatus;
import com.cpay.entities.Transaction;
import com.cpay.repositories.TransactionRepository;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {

	@Mock
	private TransactionRepository transactionRepository;

	@InjectMocks
	private TransactionServiceImpl transactionService;

	private Transaction transaction;
	private CreditCardDetails creditCardDetails;

	@BeforeEach
	void setUp() {

		creditCardDetails = new CreditCardDetails();
		creditCardDetails.setCardNumber("1234567890123456");

		transaction = new Transaction();
		transaction.setAmount(200.0);
		transaction.setTransactionType("Debit");
		transaction.setTransactionDate(LocalDate.now());
		transaction.setTransactionStatus(EPaymentStatus.COMPLETED);
		transaction.setCreditCard(creditCardDetails);
	}

	@Test
	void testRecordTransaction() {

		Mockito.when(transactionRepository.save(Mockito.any(Transaction.class))).thenReturn(transaction);

		Transaction recordedTransaction = transactionService.recordTransaction(transaction);

		assertNotNull(recordedTransaction, "Recorded transaction should not be null");
		assertEquals(200.0, recordedTransaction.getAmount(), "Transaction amount should match");
		assertEquals("Debit", recordedTransaction.getTransactionType(), "Transaction type should be 'Debit'");
		assertEquals(EPaymentStatus.COMPLETED, recordedTransaction.getTransactionStatus(),
				"Transaction status should be 'Completed'");
	}

	@Test
	void testGetTransactionsByCardNumber() {

		List<Transaction> transactions = Arrays.asList(transaction);

		Mockito.when(transactionRepository.findByCreditCardCardNumber("1234567890123456")).thenReturn(transactions);

		List<Transaction> fetchedTransactions = transactionService.getTransactionsByCardNumber("1234567890123456");

		assertNotNull(fetchedTransactions, "Transaction list should not be null");
		assertEquals(1, fetchedTransactions.size(), "There should be one transaction for the given card number");
		assertEquals("1234567890123456", fetchedTransactions.get(0).getCreditCard().getCardNumber(),
				"Card number should match");
		assertEquals(200.0, fetchedTransactions.get(0).getAmount(), "Transaction amount should match");
		assertEquals(EPaymentStatus.COMPLETED, fetchedTransactions.get(0).getTransactionStatus(),
				"Transaction status should match");
	}

	@Test
	void testGetTransactionsByCardNumber_NotFound() {

		Mockito.when(transactionRepository.findByCreditCardCardNumber("0000000000000000")).thenReturn(Arrays.asList());

		List<Transaction> fetchedTransactions = transactionService.getTransactionsByCardNumber("0000000000000000");

		assertNotNull(fetchedTransactions, "Transaction list should not be null");
		assertTrue(fetchedTransactions.isEmpty(), "Transaction list should be empty for a non-existent card number");
	}
}
