package com.cpay.service;

import java.time.LocalDate;
import java.util.Optional;
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

		CreditCardApplication savedApplication = applicationRepository.save(application);

		// Generate a Random Order ID (e.g., between 100000 and 999999)
		Random random = new Random();
		Long randomOrderId = 100000L + (long) (random.nextInt(900000));

		OrderTracking orderTracking = createOrderTracking(savedApplication, randomOrderId);

		orderTrackingRepository.save(orderTracking);

		updateOrderStatusBasedOnApplicationStatus(savedApplication);

		return savedApplication;
	}

	@Override
	public Iterable<CreditCardApplication> getApplicationsByUserId(String username) {

		return applicationRepository.findByUsername(username);
	}

	@Override
	public CreditCardApplication getApplicationById(Long id) {
		return applicationRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("CreditCardApplication not found with ID: " + id));
	}

	@Override
	public CreditCardApplication getApplicationByOrderId(String orderId) {
		try {
			// Convert orderId from String to Long
			Long orderIdLong = Long.parseLong(orderId);

			// Find the OrderTracking by orderId (converted to Long)
			Optional<OrderTracking> orderTrackingOptional = orderTrackingRepository.findByOrderId(orderIdLong);

			if (orderTrackingOptional.isPresent()) {
				return orderTrackingOptional.get().getCreditCardApplication(); // Return associated
																				// CreditCardApplication
			}
		} catch (NumberFormatException e) {

			throw new IllegalArgumentException("Invalid Order ID format: " + orderId);
		}

		return null;
	}

	@Override
	public CreditCardApplication approveApplication(Long applicationId) {
		CreditCardApplication application = applicationRepository.findById(applicationId)
				.orElseThrow(() -> new ResourceNotFoundException("Application not found with ID: " + applicationId));

		// Change application status to APPROVED
		application.setApplicationStatus(EApplicationStatus.APPROVED);

		// Update the corresponding order status to DISPATCHED
		updateOrderStatusBasedOnApplicationStatus(application);

		return applicationRepository.save(application);
	}

	@Override
	public CreditCardApplication rejectApplication(Long applicationId) {
		CreditCardApplication application = applicationRepository.findById(applicationId)
				.orElseThrow(() -> new ResourceNotFoundException("Application not found with ID: " + applicationId));

		// Change application status to REJECTED
		application.setApplicationStatus(EApplicationStatus.REJECTED);

		// Update the corresponding order status to CANCELLED
		updateOrderStatusBasedOnApplicationStatus(application);

		return applicationRepository.save(application);
	}

	// Implement the createOrderTracking method as required by the interface
	@Override
	public OrderTracking createOrderTracking(CreditCardApplication application, Long orderId) {
		OrderTracking orderTracking = new OrderTracking();
		orderTracking.setOrderStatus(EOrderStatus.PENDING); // Default status as pending
		orderTracking.setOrderDate(LocalDate.now()); // Set today's date
		orderTracking.setOrderId(orderId); // Set the generated random order ID
		orderTracking.setCreditCardApplication(application); // Link to CreditCardApplication
		return orderTracking;
	}

	// Helper method to update the order status based on application status
	public void updateOrderStatusBasedOnApplicationStatus(CreditCardApplication application) {
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
			// If any other status, no change to order status
			break;
		}

		// Save the updated OrderTracking
		orderTrackingRepository.save(orderTracking);
	}
}
