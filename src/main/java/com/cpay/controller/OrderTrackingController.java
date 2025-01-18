package com.cpay.controller;

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

	@Autowired
	private OrderTrackingService orderTrackingService;

	@Autowired
	private CreditCardApplicationService creditCardApplicationService;

	@PreAuthorize("hasRole('Customer')")
	@GetMapping("/track/{orderId}")
	public ResponseEntity<?> trackOrder(@PathVariable Long orderId) {
		try {
			OrderTracking orderTracking = orderTrackingService.trackOrder(orderId);
			return ResponseEntity.ok(orderTracking);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order with ID " + orderId + " not found.");
		}
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/allOrdersWithApplications")
	public ResponseEntity<List<OrderTracking>> getAllOrdersWithApplications() {
		List<OrderTracking> orders = orderTrackingService.getAllOrders();

		for (OrderTracking order : orders) {

			CreditCardApplication application = creditCardApplicationService
					.getApplicationById(order.getCreditCardApplication().getId());
			order.setCreditCardApplication(application);
		}

		return ResponseEntity.ok(orders);
	}

	// @PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/updateStatus/{orderId}/status")
	public ResponseEntity<?> updateOrderStatus(@PathVariable Long orderId, @RequestBody String status) {
		try {
			OrderTracking updatedOrder = orderTrackingService.updateOrderStatus(orderId, status);
			return ResponseEntity.ok(updatedOrder);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order with ID " + orderId + " not found.");
		}
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/admin")
	public ResponseEntity<OrderTracking> createOrder(@RequestBody OrderTracking orderTracking) {
		try {
			OrderTracking createdOrder = orderTrackingService.createOrder(orderTracking);
			return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/admin/{orderId}")
	public ResponseEntity<?> deleteOrder(@PathVariable Long orderId) {
		try {
			orderTrackingService.deleteOrder(orderId);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Order with ID " + orderId + " deleted.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order with ID " + orderId + " not found.");
		}
	}

	@PreAuthorize("hasRole('Admin')")
	@GetMapping("/byApplication/{applicationId}")
	public ResponseEntity<OrderTracking> getOrderTrackingByApplication(@PathVariable Long applicationId) {
		try {
			CreditCardApplication application = creditCardApplicationService.getApplicationById(applicationId);
			OrderTracking orderTracking = orderTrackingService.getOrderTrackingByApplication(application);
			return ResponseEntity.ok(orderTracking);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
}
