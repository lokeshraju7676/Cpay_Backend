package com.cpay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpay.entities.CreditCardApplication;
import com.cpay.entities.ERole.EApplicationStatus;
import com.cpay.exceptions.ResourceNotFoundException;
import com.cpay.repositories.CreditCardApplicationRepository;

@Service
public class CreditCardApplicationServiceImpl implements CreditCardApplicationService {

    @Autowired
    private CreditCardApplicationRepository applicationRepository;

    @Override
    public CreditCardApplication applyForCreditCard(CreditCardApplication application) {
        return applicationRepository.save(application);
    }

    @Override
    public Iterable<CreditCardApplication> getApplicationsByUserId(String username) {
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
}
