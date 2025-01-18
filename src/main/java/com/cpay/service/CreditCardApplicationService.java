package com.cpay.service;

import com.cpay.entities.CreditCardApplication;
import com.cpay.entities.OrderTracking;

public interface CreditCardApplicationService {

	// Existing methods
	CreditCardApplication applyForCreditCard(CreditCardApplication application);

	Iterable<CreditCardApplication> getApplicationsByUserId(String username);

	CreditCardApplication approveApplication(Long applicationId);

	CreditCardApplication rejectApplication(Long applicationId);

	OrderTracking createOrderTracking(CreditCardApplication application, Long orderId);

	void updateOrderStatusBasedOnApplicationStatus(CreditCardApplication application);

	// Add this method to fetch an application by ID
	CreditCardApplication getApplicationById(Long id);

	// Add the new method to fetch an application by Order ID
	CreditCardApplication getApplicationByOrderId(String orderId);
}
