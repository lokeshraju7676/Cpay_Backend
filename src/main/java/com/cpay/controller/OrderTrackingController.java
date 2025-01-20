package com.cpay.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.cpay.entities.OrderTracking;
import com.cpay.entities.CreditCardApplication;
import com.cpay.service.OrderTrackingService;
import com.cpay.service.CreditCardApplicationService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin("*")
public class OrderTrackingController {

    private static final Logger logger = LoggerFactory.getLogger(OrderTrackingController.class);

    @Autowired
    private OrderTrackingService orderTrackingService;

    @Autowired
    private CreditCardApplicationService creditCardApplicationService;

    @PreAuthorize("hasRole('Customer')")
    @GetMapping("/track/{orderId}")
    public ResponseEntity<?> trackOrder(@PathVariable Long orderId) {
        logger.info("Received request to track order with ID: {}", orderId);
        try {
            OrderTracking orderTracking = orderTrackingService.trackOrder(orderId);
            logger.info("Successfully retrieved order details for order ID: {}", orderId);
            return ResponseEntity.ok(orderTracking);
        } catch (Exception e) {
            logger.error("Order with ID {} not found", orderId, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order with ID " + orderId + " not found.");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/allOrdersWithApplications")
    public ResponseEntity<List<OrderTracking>> getAllOrdersWithApplications() {
        logger.info("Received request to fetch all orders with applications.");
        List<OrderTracking> orders = orderTrackingService.getAllOrders();

        for (OrderTracking order : orders) {
            CreditCardApplication application = creditCardApplicationService
                    .getApplicationById(order.getCreditCardApplication().getId());
            order.setCreditCardApplication(application);
        }

        logger.info("Successfully retrieved {} orders with associated credit card applications.", orders.size());
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/updateStatus/{orderId}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long orderId, @RequestBody String status) {
        logger.info("Received request to update status for order ID: {} to {}", orderId, status);
        try {
            OrderTracking updatedOrder = orderTrackingService.updateOrderStatus(orderId, status);
            logger.info("Successfully updated status for order ID: {} to {}", orderId, status);
            return ResponseEntity.ok(updatedOrder);
        } catch (Exception e) {
            logger.error("Failed to update status for order ID: {}", orderId, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order with ID " + orderId + " not found.");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin")
    public ResponseEntity<OrderTracking> createOrder(@RequestBody OrderTracking orderTracking) {
        logger.info("Received request to create new order with details: {}", orderTracking);
        try {
            OrderTracking createdOrder = orderTrackingService.createOrder(orderTracking);
            logger.info("Successfully created new order with ID: {}", createdOrder.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
        } catch (Exception e) {
            logger.error("Failed to create new order with details: {}", orderTracking, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long orderId) {
        logger.info("Received request to delete order with ID: {}", orderId);
        try {
            orderTrackingService.deleteOrder(orderId);
            logger.info("Successfully deleted order with ID: {}", orderId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Order with ID " + orderId + " deleted.");
        } catch (Exception e) {
            logger.error("Failed to delete order with ID: {}", orderId, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order with ID " + orderId + " not found.");
        }
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping("/byApplication/{applicationId}")
    public ResponseEntity<OrderTracking> getOrderTrackingByApplication(@PathVariable Long applicationId) {
        logger.info("Received request to fetch order tracking by application ID: {}", applicationId);
        try {
            CreditCardApplication application = creditCardApplicationService.getApplicationById(applicationId);
            OrderTracking orderTracking = orderTrackingService.getOrderTrackingByApplication(application);
            logger.info("Successfully retrieved order tracking for application ID: {}", applicationId);
            return ResponseEntity.ok(orderTracking);
        } catch (Exception e) {
            logger.error("Failed to retrieve order tracking for application ID: {}", applicationId, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
