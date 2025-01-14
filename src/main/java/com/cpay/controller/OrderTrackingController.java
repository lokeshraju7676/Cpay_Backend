package com.cpay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpay.entities.OrderTracking;
import com.cpay.service.OrderTrackingService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/orders")
public class OrderTrackingController {

    @Autowired
    private OrderTrackingService orderTrackingService;

    @GetMapping("/track/{applicationId}")
    public ResponseEntity<OrderTracking> trackOrder(@PathVariable Long applicationId) {
        OrderTracking tracking = orderTrackingService.trackOrder(applicationId);
        return ResponseEntity.ok(tracking);
    }
}
