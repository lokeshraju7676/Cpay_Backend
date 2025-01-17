package com.cpay.service;

import com.cpay.entities.OrderTracking;

public interface OrderTrackingService {

    // Method to track an order based on its unique order ID
    OrderTracking trackOrder(Long orderId);
}
