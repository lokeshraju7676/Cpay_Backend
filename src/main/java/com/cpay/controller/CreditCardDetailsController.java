package com.cpay.controller;

import java.util.List;

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

import com.cpay.entities.CreditCardDetails;
import com.cpay.service.CreditCardDetailsService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/carddetails")
public class CreditCardDetailsController {

	@Autowired
	private CreditCardDetailsService creditCardDetailsService;

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create")
	public ResponseEntity<CreditCardDetails> createCardDetails(@RequestBody CreditCardDetails creditCardDetails) {
		CreditCardDetails savedCardDetails = creditCardDetailsService.createCardDetails(creditCardDetails);
		return ResponseEntity.ok(savedCardDetails);
	}

	//@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
	@GetMapping("/{cardNumber}")
	public ResponseEntity<CreditCardDetails> getCardDetailsByCardNumber(@PathVariable String cardNumber) {
		CreditCardDetails cardDetails = creditCardDetailsService.getCardDetailsByCardNumber(cardNumber);
		if (cardDetails != null) {
			return ResponseEntity.ok(cardDetails);
		} else {
			return ResponseEntity.status(404).body(null);
		}
	}
	
	/*
	 * // New endpoint to fetch all card details
	 * 
	 * @PreAuthorize("hasRole('CUSTOMER')")
	 * 
	 * @GetMapping("/all") public ResponseEntity<List<CreditCardDetails>>
	 * getAllCardDetails() { List<CreditCardDetails> cardDetailsList =
	 * creditCardDetailsService.getAllCardDetails(); return
	 * ResponseEntity.ok(cardDetailsList); }
	 */
}
