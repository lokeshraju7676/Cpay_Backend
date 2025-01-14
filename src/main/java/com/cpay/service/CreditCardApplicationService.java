package com.cpay.service;

import com.cpay.entities.CreditCardApplication;

public interface CreditCardApplicationService {
    CreditCardApplication applyForCreditCard(CreditCardApplication application);
    Iterable<CreditCardApplication> getApplicationsByUserId(String username);
    
    //Admin Related methods
    CreditCardApplication approveApplication(Long applicationId);
    CreditCardApplication rejectApplication(Long applicationId);
}
