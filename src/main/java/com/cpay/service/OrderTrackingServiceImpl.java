package com.cpay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpay.entities.OrderTracking;
import com.cpay.exceptions.ResourceNotFoundException;
import com.cpay.repositories.OrderTrackingRepository;

@Service
public class OrderTrackingServiceImpl implements OrderTrackingService {

    @Autowired
    private OrderTrackingRepository orderTrackingRepository;

    @Override
    public OrderTracking trackOrder(Long applicationId) {
        return orderTrackingRepository.findByCreditCardApplicationId(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Order Tracking not found for Application ID: " + applicationId));
    }
}
