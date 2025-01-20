package com.cpay.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cpay.entities.ERole.EPaymentStatus;
import com.cpay.entities.Payment;
import com.cpay.exceptions.PaymentExceptionHandler;
import com.cpay.service.PaymentService;

@ExtendWith(MockitoExtension.class)
public class PaymentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    private Payment payment;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(paymentController).setControllerAdvice(PaymentExceptionHandler.class).build();

        payment = new Payment();
        payment.setAmount(500.0);
        payment.setPaymentDate(java.time.LocalDate.of(2025, 1, 18));
        payment.setPaymentStatus(EPaymentStatus.COMPLETED);
    }

    @Test
    void testProcessPayment() throws Exception {
        when(paymentService.processPayment(any(Payment.class))).thenReturn(payment);

        mockMvc.perform(post("/api/payments/process")
                .contentType("application/json")
                .content("{ \"amount\": 500.0, \"paymentDate\": \"2025-01-18\", \"paymentStatus\": \"COMPLETED\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(500.0))
                .andExpect(jsonPath("$.paymentStatus").value("COMPLETED"))
                .andExpect(jsonPath("$.paymentDate").value("2025-01-18"));
    }

    @Test
    void testGetPaymentByCardNumber() throws Exception {
        when(paymentService.getPaymentByCardNumber(anyString())).thenReturn(payment);

        mockMvc.perform(get("/api/payments/card/1234567890123456"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(500.0))
                .andExpect(jsonPath("$.paymentStatus").value("COMPLETED"))
                .andExpect(jsonPath("$.paymentDate").value("2025-01-18"));
    }

    @Test
    void testGetPaymentByCardNumber_NotFound() throws Exception {
        // Mock the service to throw a RuntimeException to simulate payment not being found
        when(paymentService.getPaymentByCardNumber(anyString())).thenThrow(new RuntimeException("Payment not found"));

        // Send GET request to /api/payments/card/{cardNumber}
        mockMvc.perform(get("/api/payments/card/0000000000000000"))
                .andExpect(status().isNotFound())  // Expect 404 Not Found status
                .andExpect(content().string("Payment not found"));  // Expect the message: "Payment not found"
    }
}
