package com.cpay.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpay.entities.CreditCardApplication;
import com.cpay.entities.ERole;
import com.cpay.entities.OrderTracking;
import com.cpay.exceptions.ResourceNotFoundException;
import com.cpay.repositories.OrderTrackingRepository;

@Service
public class OrderTrackingServiceImpl implements OrderTrackingService {

    private static final Logger logger = LoggerFactory.getLogger(OrderTrackingServiceImpl.class);

    @Autowired
    private OrderTrackingRepository orderTrackingRepository;

    @Override
    public OrderTracking trackOrder(Long orderId) {
        logger.info("Tracking order with ID: {}", orderId);

        Optional<OrderTracking> orderTracking = orderTrackingRepository.findByOrderId(orderId);

        if (!orderTracking.isPresent()) {
            logger.error("Order not found with ID: {}", orderId);
            throw new ResourceNotFoundException("Order not found with ID: " + orderId);
        }

        logger.info("Found order with ID: {}", orderId);
        return orderTracking.get();
    }

    @Override
    public List<OrderTracking> getAllOrders() {
        logger.info("Fetching all orders");

        List<OrderTracking> orders = (List<OrderTracking>) orderTrackingRepository.findAll();

        if (orders.isEmpty()) {
            logger.warn("No orders found");
        } else {
            logger.info("Retrieved {} orders", orders.size());
        }

        return orders;
    }

    @Override
    public OrderTracking updateOrderStatus(Long orderId, String status) {
        logger.info("Updating order status for order ID: {} to {}", orderId, status);

        Optional<OrderTracking> orderTracking = orderTrackingRepository.findByOrderId(orderId);

        if (!orderTracking.isPresent()) {
            logger.error("Order not found with ID: {}", orderId);
            throw new ResourceNotFoundException("Order not found with ID: " + orderId);
        }

        OrderTracking order = orderTracking.get();
        try {
            // Assuming status is an enum value from EOrderStatus
            order.setOrderStatus(ERole.EOrderStatus.valueOf(status));
            orderTrackingRepository.save(order);
            logger.info("Order status updated to: {} for order ID: {}", status, orderId);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid status value: {}", status);
            throw new IllegalArgumentException("Invalid order status: " + status);
        }

        return order;
    }

    @Override
    public OrderTracking createOrder(OrderTracking orderTracking) {
        logger.info("Creating a new order with ID: {}", orderTracking.getOrderId());

        OrderTracking createdOrder = orderTrackingRepository.save(orderTracking);
        logger.info("Order created successfully with ID: {}", createdOrder.getOrderId());

        return createdOrder;
    }

    @Override
    public void deleteOrder(Long orderId) {
        logger.info("Deleting order with ID: {}", orderId);

        Optional<OrderTracking> orderTracking = orderTrackingRepository.findByOrderId(orderId);

        if (!orderTracking.isPresent()) {
            logger.error("Order not found with ID: {}", orderId);
            throw new ResourceNotFoundException("Order not found with ID: " + orderId);
        }

        orderTrackingRepository.deleteById(orderId);
        logger.info("Order with ID: {} deleted successfully", orderId);
    }

    @Override
    public OrderTracking getOrderTrackingByApplication(CreditCardApplication application) {
        logger.info("Fetching order tracking for application ID: {}", application.getId());

        Optional<OrderTracking> orderTracking = orderTrackingRepository.findByCreditCardApplication(application);

        if (!orderTracking.isPresent()) {
            logger.error("Order tracking not found for application ID: {}", application.getId());
            throw new ResourceNotFoundException("Order tracking not found for application: " + application.getId());
        }

        logger.info("Found order tracking for application ID: {}", application.getId());
        return orderTracking.get();
    }
}
