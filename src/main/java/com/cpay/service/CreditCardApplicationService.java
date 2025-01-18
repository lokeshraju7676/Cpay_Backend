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
}
