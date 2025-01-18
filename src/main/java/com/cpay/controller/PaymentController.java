package com.cpay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpay.entities.Payment;
import com.cpay.service.PaymentService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/payments")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	@PreAuthorize("hasRole('CUSTOMER')")
	@PostMapping("/process")
	public ResponseEntity<Payment> processPayment(@RequestBody Payment payment) {
		Payment processedPayment = paymentService.processPayment(payment);
		return ResponseEntity.ok(processedPayment);
	}

	@GetMapping("/card/{cardNumber}")
	public ResponseEntity<Payment> getPaymentByCardNumber(@PathVariable String cardNumber) {
		Payment payment = paymentService.getPaymentByCardNumber(cardNumber);
		return ResponseEntity.ok(payment);
	}
}
