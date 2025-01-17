package com.cpay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cpay.entities.OrderTracking;
import com.cpay.service.OrderTrackingService;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin("*")
public class OrderTrackingController {

    @Autowired
    private OrderTrackingService orderTrackingService;

    @GetMapping("/track/{orderId}")
    public ResponseEntity<OrderTracking> trackOrder(@PathVariable Long orderId) {
        // Track the order by orderId
        OrderTracking orderTracking = orderTrackingService.trackOrder(orderId);
        return ResponseEntity.ok(orderTracking);
    }
}
