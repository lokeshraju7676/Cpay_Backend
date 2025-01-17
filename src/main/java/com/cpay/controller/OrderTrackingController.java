package com.cpay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.cpay.entities.OrderTracking;
import com.cpay.service.OrderTrackingService;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin("*")
public class OrderTrackingController {

    @Autowired
    private OrderTrackingService orderTrackingService;

    /**
     * Track an order by its ID
     *
     * @param orderId The order ID to track
     * @return ResponseEntity containing the order details or error message
     */
    @PreAuthorize("hasRole('Customer')")
    @GetMapping("/track/{orderId}")
    public ResponseEntity<?> trackOrder(@PathVariable Long orderId) {
        // Fetch the order tracking details using the service
        OrderTracking orderTracking = orderTrackingService.trackOrder(orderId);

		
		  if (orderTracking == null) { // Return a 404 if no order is found with thegiven orderId 
			  return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order with ID " + orderId + " not found."); }
		 

        // Return the order tracking details with a 200 OK status
        return ResponseEntity.ok(orderTracking);
    }
}
