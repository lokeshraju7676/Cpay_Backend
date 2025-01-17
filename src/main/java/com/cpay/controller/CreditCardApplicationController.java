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
    
    
	/*
	 * //Admin related Methods
	 * 
	 * @PreAuthorize("hasRole('ADMIN')")
	 * 
	 * @PostMapping("/approve/{id}") public ResponseEntity<CreditCardApplication>
	 * approveApplication(@PathVariable Long id) { CreditCardApplication
	 * approvedApplication = applicationService.approveApplication(id); return
	 * ResponseEntity.ok(approvedApplication); }
	 * 
	 * @PreAuthorize("hasRole('ADMIN')")
	 * 
	 * @PostMapping("/reject/{id}") public ResponseEntity<CreditCardApplication>
	 * rejectApplication(@PathVariable Long id) { CreditCardApplication
	 * rejectedApplication = applicationService.rejectApplication(id); return
	 * ResponseEntity.ok(rejectedApplication); }
	 */    
    
}
