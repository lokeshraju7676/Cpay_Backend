package com.cpay.service;

import com.cpay.entities.OrderTracking;

public interface OrderTrackingService {
    OrderTracking trackOrder(Long applicationId);
}
