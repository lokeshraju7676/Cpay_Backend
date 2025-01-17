package com.cpay.service;

import java.time.LocalDate;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpay.entities.CreditCardApplication;
import com.cpay.entities.ERole.EApplicationStatus;
import com.cpay.entities.ERole.EOrderStatus;
import com.cpay.entities.OrderTracking;
import com.cpay.exceptions.ResourceNotFoundException;
import com.cpay.repositories.CreditCardApplicationRepository;
import com.cpay.repositories.OrderTrackingRepository;

@Service
public class CreditCardApplicationServiceImpl implements CreditCardApplicationService {

    @Autowired
    private CreditCardApplicationRepository applicationRepository;

    @Autowired
    private OrderTrackingRepository orderTrackingRepository;

    @Override
    public CreditCardApplication applyForCreditCard(CreditCardApplication application) {
        // Step 1: Save the CreditCardApplication
        CreditCardApplication savedApplication = applicationRepository.save(application);

        // Step 2: Generate a Random Order ID (e.g., between 100000 and 999999)
        Random random = new Random();
        Long randomOrderId = 100000L + (long) (random.nextInt(900000));

        // Step 3: Create the associated OrderTracking
        OrderTracking orderTracking = new OrderTracking();
        orderTracking.setOrderStatus(EOrderStatus.PENDING);  // Initial status 'PENDING'
        orderTracking.setOrderDate(LocalDate.now());  // Set current date as order date
        orderTracking.setOrderId(randomOrderId);  // Set the random order ID
        orderTracking.setCreditCardApplication(savedApplication);  // Link order tracking to the application

        // Step 4: Save the OrderTracking
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
        CreditCardApplication application = applicationRepository.findById(applicationId).orElseThrow(() -> 
            new ResourceNotFoundException("Application not found with ID: " + applicationId));
        application.setApplicationStatus(EApplicationStatus.APPROVED);
        return applicationRepository.save(application);
    }

    @Override
    public CreditCardApplication rejectApplication(Long applicationId) {
        CreditCardApplication application = applicationRepository.findById(applicationId).orElseThrow(() -> 
            new ResourceNotFoundException("Application not found with ID: " + applicationId));
        application.setApplicationStatus(EApplicationStatus.REJECTED);
        return applicationRepository.save(application);
    }

    // Helper method to create an OrderTracking entity
    @Override
    public OrderTracking createOrderTracking(CreditCardApplication application) {
        OrderTracking orderTracking = new OrderTracking();
        orderTracking.setOrderStatus(EOrderStatus.PENDING); // Default status as pending
        orderTracking.setOrderDate(LocalDate.now()); // Set today's date
        orderTracking.setCreditCardApplication(application); // Link to CreditCardApplication

        return orderTracking;
    }
}
