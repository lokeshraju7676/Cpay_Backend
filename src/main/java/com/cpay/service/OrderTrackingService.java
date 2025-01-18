package com.cpay.service;

import java.util.List;

import com.cpay.entities.CreditCardApplication;
import com.cpay.entities.OrderTracking;

public interface OrderTrackingService {

	OrderTracking trackOrder(Long orderId);

	List<OrderTracking> getAllOrders();

	OrderTracking updateOrderStatus(Long orderId, String status);

	OrderTracking createOrder(OrderTracking orderTracking);

	void deleteOrder(Long orderId);

	OrderTracking getOrderTrackingByApplication(CreditCardApplication application);
}
