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

import com.cpay.entities.CreditCardApplication;
import com.cpay.service.CreditCardApplicationService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/applications")
public class CreditCardApplicationController {

	@Autowired
	private CreditCardApplicationService applicationService;

	@PreAuthorize("hasRole('Customer')")
	@PostMapping("/apply")
	public ResponseEntity<CreditCardApplication> applyForCreditCard(@RequestBody CreditCardApplication application) {
		CreditCardApplication savedApplication = applicationService.applyForCreditCard(application);
		return ResponseEntity.ok(savedApplication);
	}

	@GetMapping("/{username}")
	public ResponseEntity<Iterable<CreditCardApplication>> getApplicationsByUserId(@PathVariable String username) {
		Iterable<CreditCardApplication> applications = applicationService.getApplicationsByUserId(username);
		return ResponseEntity.ok(applications);
	}

	// New endpoint to get application details by Order ID
	@PreAuthorize("hasRole('Admin')")
	@GetMapping("/details/{orderId}")
	public ResponseEntity<CreditCardApplication> getApplicationByOrderId(@PathVariable String orderId) {
		CreditCardApplication application = applicationService.getApplicationByOrderId(orderId);
		if (application != null) {
			return ResponseEntity.ok(application);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
