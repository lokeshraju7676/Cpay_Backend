package com.cpay.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.cpay.entities.CreditCardApplication;
import com.cpay.entities.ERole;
import com.cpay.entities.OrderTracking;
import com.cpay.service.CreditCardApplicationService;
import com.cpay.service.OrderTrackingService;

@ExtendWith(MockitoExtension.class)
public class OrderTrackingControllerTest {

	@Mock
	private OrderTrackingService orderTrackingService;

	@Mock
	private CreditCardApplicationService creditCardApplicationService;

	@InjectMocks
	private OrderTrackingController orderTrackingController;

	private OrderTracking orderTracking;
	private CreditCardApplication creditCardApplication;

	@BeforeEach
	void setUp() {

		creditCardApplication = new CreditCardApplication();
		creditCardApplication.setApplicantName("John Doe");
		creditCardApplication.setApplicantEmail("john.doe@example.com");
		creditCardApplication.setUsername("testUser");

		orderTracking = new OrderTracking();
		orderTracking.setOrderStatus(ERole.EOrderStatus.PENDING);
		orderTracking.setOrderDate(LocalDate.now());
		orderTracking.setOrderId(123L);
		orderTracking.setCreditCardApplication(creditCardApplication);
	}

	@SuppressWarnings("deprecation")
	@Test
	void testTrackOrder() {
		Long orderId = 123L;
		when(orderTrackingService.trackOrder(orderId)).thenReturn(orderTracking);

		ResponseEntity<?> response = orderTrackingController.trackOrder(orderId);

		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(orderTracking, response.getBody());

		verify(orderTrackingService, times(1)).trackOrder(orderId);
	}

	@SuppressWarnings("deprecation")
	@Test
	void testTrackOrderNotFound() {
		Long orderId = 123L;
		when(orderTrackingService.trackOrder(orderId)).thenThrow(new RuntimeException("Order not found"));

		ResponseEntity<?> response = orderTrackingController.trackOrder(orderId);

		assertNotNull(response);
		assertEquals(404, response.getStatusCodeValue());
		assertTrue(response.getBody().toString().contains("Order with ID 123 not found"));

		verify(orderTrackingService, times(1)).trackOrder(orderId);
	}

	@SuppressWarnings("deprecation")
	@Test
	void testGetAllOrdersWithApplications() {
		when(orderTrackingService.getAllOrders()).thenReturn(List.of(orderTracking));
		when(creditCardApplicationService.getApplicationById(orderTracking.getCreditCardApplication().getId()))
				.thenReturn(creditCardApplication);

		ResponseEntity<List<OrderTracking>> response = orderTrackingController.getAllOrdersWithApplications();

		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(1, response.getBody().size());
		assertEquals(orderTracking, response.getBody().get(0));

		verify(orderTrackingService, times(1)).getAllOrders();
		verify(creditCardApplicationService, times(1))
				.getApplicationById(orderTracking.getCreditCardApplication().getId());
	}

	@SuppressWarnings("deprecation")
	@Test
	void testUpdateOrderStatus() {
		Long orderId = 123L;
		String status = "Shipped";
		when(orderTrackingService.updateOrderStatus(orderId, status)).thenReturn(orderTracking);

		ResponseEntity<?> response = orderTrackingController.updateOrderStatus(orderId, status);

		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(orderTracking, response.getBody());

		verify(orderTrackingService, times(1)).updateOrderStatus(orderId, status);
	}

	@SuppressWarnings("deprecation")
	@Test
	void testCreateOrder() {
		when(orderTrackingService.createOrder(any(OrderTracking.class))).thenReturn(orderTracking);

		ResponseEntity<OrderTracking> response = orderTrackingController.createOrder(orderTracking);

		assertNotNull(response);
		assertEquals(201, response.getStatusCodeValue());
		assertEquals(orderTracking, response.getBody());

		verify(orderTrackingService, times(1)).createOrder(any(OrderTracking.class));
	}

	@SuppressWarnings("deprecation")
	@Test
	void testDeleteOrder() {
		Long orderId = 123L;
		doNothing().when(orderTrackingService).deleteOrder(orderId);

		ResponseEntity<?> response = orderTrackingController.deleteOrder(orderId);

		assertNotNull(response);
		assertEquals(204, response.getStatusCodeValue());
		assertTrue(response.getBody().toString().contains("Order with ID 123 deleted"));

		verify(orderTrackingService, times(1)).deleteOrder(orderId);
	}

	@SuppressWarnings("deprecation")
	@Test
	void testGetOrderTrackingByApplication() {
		Long applicationId = 1L;
		when(creditCardApplicationService.getApplicationById(applicationId)).thenReturn(creditCardApplication);
		when(orderTrackingService.getOrderTrackingByApplication(any(CreditCardApplication.class)))
				.thenReturn(orderTracking);

		ResponseEntity<OrderTracking> response = orderTrackingController.getOrderTrackingByApplication(applicationId);

		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(orderTracking, response.getBody());

		verify(creditCardApplicationService, times(1)).getApplicationById(applicationId);
		verify(orderTrackingService, times(1)).getOrderTrackingByApplication(any(CreditCardApplication.class));
	}

}
