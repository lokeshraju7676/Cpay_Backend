package com.cpay.service;

import com.cpay.entities.CreditCardApplication;
import com.cpay.entities.OrderTracking;

public interface CreditCardApplicationService {

    CreditCardApplication applyForCreditCard(CreditCardApplication application);

    Iterable<CreditCardApplication> getApplicationsByUserId(String username);

    // Admin Related methods
    CreditCardApplication approveApplication(Long applicationId);

    CreditCardApplication rejectApplication(Long applicationId);

    // New method to create order tracking
    OrderTracking createOrderTracking(CreditCardApplication application);
}
