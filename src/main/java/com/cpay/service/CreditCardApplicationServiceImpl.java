package com.cpay.service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(CreditCardApplicationServiceImpl.class);

    @Autowired
    private CreditCardApplicationRepository applicationRepository;

    @Autowired
    private OrderTrackingRepository orderTrackingRepository;

    @Override
    public CreditCardApplication applyForCreditCard(CreditCardApplication application) {
        logger.info("Applying for a new credit card for user: {}", application.getUsername());

        CreditCardApplication savedApplication = applicationRepository.save(application);
        logger.info("Credit card application saved with ID: {}", savedApplication.getId());

        // Generate a Random Order ID (e.g., between 100000 and 999999)
        Random random = new Random();
        Long randomOrderId = 100000L + (long) (random.nextInt(900000));

        OrderTracking orderTracking = createOrderTracking(savedApplication, randomOrderId);
        orderTrackingRepository.save(orderTracking);
        logger.info("OrderTracking created for application ID: {}", savedApplication.getId());

        updateOrderStatusBasedOnApplicationStatus(savedApplication);

        return savedApplication;
    }

    @Override
    public Iterable<CreditCardApplication> getApplicationsByUserId(String username) {
        logger.info("Fetching applications for user: {}", username);
        Iterable<CreditCardApplication> applications = applicationRepository.findByUsername(username);
        logger.info("Retrieved {} applications for user: {}", ((java.util.Collection<?>) applications).size(), username);
        return applications;
    }

    @Override
    public CreditCardApplication getApplicationById(Long id) {
        logger.info("Fetching credit card application by ID: {}", id);
        CreditCardApplication application = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CreditCardApplication not found with ID: " + id));
        logger.info("Credit card application found with ID: {}", id);
        return application;
    }

    @Override
    public CreditCardApplication getApplicationByOrderId(String orderId) {
        try {
            // Convert orderId from String to Long
            Long orderIdLong = Long.parseLong(orderId);
            logger.info("Fetching application associated with order ID: {}", orderIdLong);

            Optional<OrderTracking> orderTrackingOptional = orderTrackingRepository.findByOrderId(orderIdLong);

            if (orderTrackingOptional.isPresent()) {
                logger.info("Found application associated with order ID: {}", orderIdLong);
                return orderTrackingOptional.get().getCreditCardApplication();
            } else {
                logger.warn("No application found for order ID: {}", orderIdLong);
            }
        } catch (NumberFormatException e) {
            logger.error("Invalid Order ID format: {}", orderId, e);
            throw new IllegalArgumentException("Invalid Order ID format: " + orderId);
        }

        return null;
    }

    @Override
    public CreditCardApplication approveApplication(Long applicationId) {
        logger.info("Approving application with ID: {}", applicationId);
        CreditCardApplication application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with ID: " + applicationId));

        // Change application status to APPROVED
        application.setApplicationStatus(EApplicationStatus.APPROVED);

        // Update the corresponding order status to DISPATCHED
        updateOrderStatusBasedOnApplicationStatus(application);

        CreditCardApplication updatedApplication = applicationRepository.save(application);
        logger.info("Application with ID: {} has been approved", applicationId);

        return updatedApplication;
    }

    @Override
    public CreditCardApplication rejectApplication(Long applicationId) {
        logger.info("Rejecting application with ID: {}", applicationId);
        CreditCardApplication application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with ID: " + applicationId));

        // Change application status to REJECTED
        application.setApplicationStatus(EApplicationStatus.REJECTED);

        // Update the corresponding order status to CANCELLED
        updateOrderStatusBasedOnApplicationStatus(application);

        CreditCardApplication updatedApplication = applicationRepository.save(application);
        logger.info("Application with ID: {} has been rejected", applicationId);

        return updatedApplication;
    }

    @Override
    public OrderTracking createOrderTracking(CreditCardApplication application, Long orderId) {
        logger.info("Creating OrderTracking for application with ID: {}", application.getId());

        OrderTracking orderTracking = new OrderTracking();
        orderTracking.setOrderStatus(EOrderStatus.PENDING); // Default status as pending
        orderTracking.setOrderDate(LocalDate.now()); // Set today's date
        orderTracking.setOrderId(orderId); // Set the generated random order ID
        orderTracking.setCreditCardApplication(application); // Link to CreditCardApplication
        return orderTracking;
    }

    public void updateOrderStatusBasedOnApplicationStatus(CreditCardApplication application) {
        logger.info("Updating order status based on application status for application ID: {}", application.getId());

        // Fetch the associated OrderTracking entity
        OrderTracking orderTracking = orderTrackingRepository.findByCreditCardApplication(application)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "OrderTracking not found for Application ID: " + application.getId()));

        // Set the order status based on the application status
        switch (application.getApplicationStatus()) {
            case APPROVED:
                orderTracking.setOrderStatus(EOrderStatus.DISPATCHED); // Approved = Dispatched
                break;
            case REJECTED:
                orderTracking.setOrderStatus(EOrderStatus.CANCELLED); // Rejected = Cancelled
                break;
            case PENDING:
                orderTracking.setOrderStatus(EOrderStatus.PENDING); // Pending = Pending
                break;
            case COMPLETED:
                orderTracking.setOrderStatus(EOrderStatus.COMPLETED); // Completed = Completed
                break;
            default:
                logger.warn("Unknown application status, no change to order status for application ID: {}", application.getId());
                break;
        }

        // Save the updated OrderTracking
        orderTrackingRepository.save(orderTracking);
        logger.info("Order status updated for application ID: {}", application.getId());
    }

    @Override
    public Iterable<CreditCardApplication> getAllApplications() {
        logger.info("Fetching all credit card applications.");
        return applicationRepository.findAll(); // Fetch all applications from the repository
    }

    @Override
    public CreditCardApplication updateApplication(Long id, CreditCardApplication updatedApplication) {
        logger.info("Updating credit card application with ID: {}", id);

        // Fetch the existing application
        CreditCardApplication existingApplication = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found with id: " + id));

        // Update the fields
        existingApplication.setApplicantName(updatedApplication.getApplicantName());
        existingApplication.setApplicantEmail(updatedApplication.getApplicantEmail());
        existingApplication.setAnnualIncome(updatedApplication.getAnnualIncome());
        existingApplication.setEmploymentStatus(updatedApplication.getEmploymentStatus());
        existingApplication.setMobileNumber(updatedApplication.getMobileNumber());
        existingApplication.setAddress(updatedApplication.getAddress());
        existingApplication.setApplicationStatus(updatedApplication.getApplicationStatus());
        existingApplication.setUsername(updatedApplication.getUsername());

        // Save the updated application
        CreditCardApplication updatedApp = applicationRepository.save(existingApplication);
        logger.info("Credit card application with ID: {} updated successfully.", id);

        return updatedApp;
    }

    @Override
    public boolean deleteApplication(Long id) {
        logger.info("Deleting credit card application with ID: {}", id);

        if (applicationRepository.existsById(id)) {
            applicationRepository.deleteById(id);
            logger.info("Credit card application with ID: {} deleted successfully.", id);
            return true; // Successfully deleted
        }

        logger.warn("Credit card application with ID: {} not found for deletion.", id);
        return false; // Application not found
    }
}
