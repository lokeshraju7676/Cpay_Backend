package com.cpay.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpay.entities.CreditCardApplication;
import com.cpay.service.CreditCardApplicationService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/applications")
public class CreditCardApplicationController {

    // Create a logger instance for the class
    private static final Logger logger = LoggerFactory.getLogger(CreditCardApplicationController.class);

    @Autowired
    private CreditCardApplicationService applicationService;

    //@PreAuthorize("hasRole('Customer')") // Uncomment to restrict access to Customer role
    @PostMapping("/apply")
    public ResponseEntity<CreditCardApplication> applyForCreditCard(@RequestBody CreditCardApplication application) {
        logger.info("Received credit card application request for user: {}", application.getUsername());
        
        try {
            CreditCardApplication savedApplication = applicationService.applyForCreditCard(application);
            logger.info("Successfully saved credit card application for user: {}", application.getUsername());
            return ResponseEntity.ok(savedApplication);
        } catch (Exception e) {
            logger.error("Error occurred while applying for credit card for user: {}. Error: {}", application.getUsername(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<Iterable<CreditCardApplication>> getApplicationsByUserId(@PathVariable String username) {
        logger.info("Fetching credit card applications for user: {}", username);

        try {
            Iterable<CreditCardApplication> applications = applicationService.getApplicationsByUserId(username);
            if (applications.iterator().hasNext()) {
                logger.info("Found {} credit card applications for user: {}", applications.spliterator().estimateSize(), username);
                return ResponseEntity.ok(applications);
            } else {
                logger.warn("No credit card applications found for user: {}", username);
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            logger.error("Error occurred while fetching applications for user: {}. Error: {}", username, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping("/details/{orderId}")
    public ResponseEntity<CreditCardApplication> getApplicationByOrderId(@PathVariable String orderId) {
        logger.info("Fetching details for credit card application with orderId: {}", orderId);

        try {
            CreditCardApplication application = applicationService.getApplicationByOrderId(orderId);
            if (application != null) {
                logger.info("Found credit card application for orderId: {}", orderId);
                return ResponseEntity.ok(application);
            } else {
                logger.warn("No credit card application found for orderId: {}", orderId);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error occurred while fetching application for orderId: {}. Error: {}", orderId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping("/all")
    public ResponseEntity<Iterable<CreditCardApplication>> getAllApplications() {
        logger.info("Fetching all credit card applications");

        try {
            Iterable<CreditCardApplication> applications = applicationService.getAllApplications();
            logger.info("Found {} credit card applications", applications.spliterator().estimateSize());
            return ResponseEntity.ok(applications);
        } catch (Exception e) {
            logger.error("Error occurred while fetching all applications. Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CreditCardApplication> updateApplication(@PathVariable("id") Long id, @RequestBody CreditCardApplication updatedApplication) {
        logger.info("Updating credit card application with id: {}", id);

        try {
            CreditCardApplication application = applicationService.updateApplication(id, updatedApplication);
            logger.info("Successfully updated credit card application with id: {}", id);
            return new ResponseEntity<>(application, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while updating application with id: {}. Error: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCreditCardApplication(@PathVariable Long id) {
        logger.info("Deleting credit card application with id: {}", id);

        try {
            boolean isDeleted = applicationService.deleteApplication(id);
            if (isDeleted) {
                logger.info("Successfully deleted credit card application with id: {}", id);
                return ResponseEntity.noContent().build();
            } else {
                logger.warn("No credit card application found to delete with id: {}", id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error occurred while deleting application with id: {}. Error: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
