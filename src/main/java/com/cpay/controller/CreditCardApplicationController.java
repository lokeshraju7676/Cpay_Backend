package com.cpay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // Correct import
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.cpay.entities.CreditCardApplication;
import com.cpay.service.CreditCardApplicationService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/applications")
public class CreditCardApplicationController {

    @Autowired
    private CreditCardApplicationService applicationService;

    //@PreAuthorize("hasRole('Customer')")
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

    // New Method to Fetch All Credit Card Applications
    @PreAuthorize("hasRole('Admin')")
    @GetMapping("/all")
    public ResponseEntity<Iterable<CreditCardApplication>> getAllApplications() {
        Iterable<CreditCardApplication> applications = applicationService.getAllApplications();
        return ResponseEntity.ok(applications);
    }
    
    // Update existing application
    @PutMapping("/{id}")
    public ResponseEntity<CreditCardApplication> updateApplication(@PathVariable("id") Long id, @RequestBody CreditCardApplication updatedApplication) {
        CreditCardApplication application = applicationService.updateApplication(id, updatedApplication);
        return new ResponseEntity<>(application, HttpStatus.OK); // Using correct HttpStatus
    }

    // Endpoint to delete a credit card application
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCreditCardApplication(@PathVariable Long id) {
        boolean isDeleted = applicationService.deleteApplication(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
