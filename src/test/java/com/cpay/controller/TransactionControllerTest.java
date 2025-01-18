package com.cpay.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cpay.entities.Transaction;
import com.cpay.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {

	private MockMvc mockMvc;

	@Mock
	private TransactionService transactionService;

	@InjectMocks
	private TransactionController transactionController;

	private List<Transaction> transactions;

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();

		Transaction transaction1 = new Transaction();
		transaction1.setId(1L);
		transaction1.setAmount(500.0);
		transaction1.setTransactionType("Credit");

		Transaction transaction2 = new Transaction();
		transaction2.setId(2L);
		transaction2.setAmount(200.0);
		transaction2.setTransactionType("Debit");

		transactions = Arrays.asList(transaction1, transaction2);
	}

	@Test
	public void testGetTransactionsByCardNumber() throws Exception {

		when(transactionService.getTransactionsByCardNumber(anyString())).thenReturn(transactions);

		mockMvc.perform(get("/api/transactions/card/1234567890123456")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].amount").value(500.0))
				.andExpect(jsonPath("$[0].transactionType").value("Credit"))
				.andExpect(jsonPath("$[1].amount").value(200.0))
				.andExpect(jsonPath("$[1].transactionType").value("Debit"));
	}

	@Test
	public void testGetTransactionsByCardNumber_NotFound() throws Exception {

		when(transactionService.getTransactionsByCardNumber(anyString())).thenReturn(Arrays.asList());

		mockMvc.perform(get("/api/transactions/card/1234567890123456")).andExpect(status().isOk())
				.andExpect(content().json("[]"));
	}
}
