package com.cpay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpay.entities.CreditCardApplication;
import com.cpay.entities.OrderTracking;
import com.cpay.entities.ERole.EApplicationStatus;
import com.cpay.entities.ERole.EOrderStatus;
import com.cpay.exceptions.ResourceNotFoundException;
import com.cpay.repositories.CreditCardApplicationRepository;
import com.cpay.repositories.OrderTrackingRepository;

import java.time.LocalDate;

@Service
public class CreditCardApplicationServiceImpl implements CreditCardApplicationService {

    @Autowired
    private CreditCardApplicationRepository applicationRepository;

    @Autowired
    private OrderTrackingRepository orderTrackingRepository;

    @Override
    public CreditCardApplication applyForCreditCard(CreditCardApplication application) {
        // Step 1: Save the CreditCardApplication first
        CreditCardApplication savedApplication = applicationRepository.save(application);

        // Step 2: Create the associated OrderTracking
        OrderTracking orderTracking = new OrderTracking();
        orderTracking.setOrderStatus(EOrderStatus.PENDING);  // Set initial status to 'Pending'
        orderTracking.setOrderDate(LocalDate.now());  // Set order date to the current date
        orderTracking.setCreditCardApplication(savedApplication);  // Link this OrderTracking with the saved application

        // Step 3: Save the OrderTracking to associate it with the application_id in the database
        orderTrackingRepository.save(orderTracking);

        // Return the saved CreditCardApplication
        return savedApplication;
    }

    @Override
    public Iterable<CreditCardApplication> getApplicationsByUserId(String username) {
        // Fetch applications by the username
        return applicationRepository.findByUsername(username);
    }

    @Override
    public CreditCardApplication approveApplication(Long applicationId) {
        CreditCardApplication application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Credit Card Application not found with ID: " + applicationId));
        application.setApplicationStatus(EApplicationStatus.APPROVED);
        return applicationRepository.save(application);
    }

    @Override
    public CreditCardApplication rejectApplication(Long applicationId) {
        CreditCardApplication application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Credit Card Application not found with ID: " + applicationId));
        application.setApplicationStatus(EApplicationStatus.REJECTED);
        return applicationRepository.save(application);
    }

    // Helper method to create an OrderTracking entity
    public OrderTracking createOrderTracking(CreditCardApplication application) {
        OrderTracking orderTracking = new OrderTracking();
        orderTracking.setOrderStatus(EOrderStatus.PENDING); // Default status as pending
        orderTracking.setOrderDate(LocalDate.now()); // Set today's date
        orderTracking.setCreditCardApplication(application); // Link to CreditCardApplication

        return orderTracking;
    }
}
