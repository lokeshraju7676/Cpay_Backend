package com.cpay.service;

import java.util.List;

import com.cpay.entities.CreditCardApplication;
import com.cpay.entities.OrderTracking;

public interface OrderTrackingService {

    // Method to track an order based on its unique order ID
    OrderTracking trackOrder(Long orderId);

    // Method to get all orders (for admin)
    List<OrderTracking> getAllOrders();

    // Method to update the status of an order
    OrderTracking updateOrderStatus(Long orderId, String status);

    // Method to create a new order
    OrderTracking createOrder(OrderTracking orderTracking);

    // Method to delete an order by its ID
    void deleteOrder(Long orderId);
    
 // Get order tracking by CreditCardApplication
    OrderTracking getOrderTrackingByApplication(CreditCardApplication application);
}
