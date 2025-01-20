package com.cpay.service;

import com.cpay.entities.CreditCardApplication;
import com.cpay.entities.OrderTracking;

public interface CreditCardApplicationService {

	CreditCardApplication applyForCreditCard(CreditCardApplication application);

	Iterable<CreditCardApplication> getApplicationsByUserId(String username);

	CreditCardApplication approveApplication(Long applicationId);

	CreditCardApplication rejectApplication(Long applicationId);

	OrderTracking createOrderTracking(CreditCardApplication application, Long orderId);

	void updateOrderStatusBasedOnApplicationStatus(CreditCardApplication application);

	CreditCardApplication getApplicationById(Long id);

	CreditCardApplication getApplicationByOrderId(String orderId);
	
	Iterable<CreditCardApplication> getAllApplications();
	
	 // New methods for update and delete
    CreditCardApplication updateApplication(Long id, CreditCardApplication updatedApplication);

	
	 // New method to delete a credit card application by ID
    boolean deleteApplication(Long id);  // Method signature
}
