package com.cpay.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpay.entities.CreditCardApplication;
import com.cpay.entities.ERole;
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

		return orderTracking.get();
	}

	public List<OrderTracking> getAllOrders() {
		return (List<OrderTracking>) orderTrackingRepository.findAll();
	}

	public OrderTracking updateOrderStatus(Long orderId, String status) {
		Optional<OrderTracking> orderTracking = orderTrackingRepository.findByOrderId(orderId);

		if (!orderTracking.isPresent()) {
			throw new ResourceNotFoundException("Order not found with ID: " + orderId);
		}

		OrderTracking order = orderTracking.get();
		// Assuming status is an enum value from EOrderStatus
		order.setOrderStatus(ERole.EOrderStatus.valueOf(status));
		return orderTrackingRepository.save(order);
	}

	public OrderTracking createOrder(OrderTracking orderTracking) {
		return orderTrackingRepository.save(orderTracking);
	}

	public void deleteOrder(Long orderId) {
		Optional<OrderTracking> orderTracking = orderTrackingRepository.findByOrderId(orderId);

		if (!orderTracking.isPresent()) {
			throw new ResourceNotFoundException("Order not found with ID: " + orderId);
		}

		orderTrackingRepository.deleteById(orderId); // Deletes the order from the database
	}

	@Override
	public OrderTracking getOrderTrackingByApplication(CreditCardApplication application) {
		Optional<OrderTracking> orderTracking = orderTrackingRepository.findByCreditCardApplication(application);

		if (!orderTracking.isPresent()) {
			throw new ResourceNotFoundException("Order tracking not found for application: " + application.getId());
		}

		return orderTracking.get();
	}
}
