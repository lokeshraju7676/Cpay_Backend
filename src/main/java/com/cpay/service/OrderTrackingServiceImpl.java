package com.cpay.service;

import java.util.Optional;

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
    public OrderTracking trackOrder(Long orderId) {
        Optional<OrderTracking> orderTracking = orderTrackingRepository.findByOrderId(orderId);

        if (!orderTracking.isPresent()) {
            throw new ResourceNotFoundException("Order not found with ID: " + orderId);
        }

        // Return the found order tracking
        return orderTracking.get();
    }
}
