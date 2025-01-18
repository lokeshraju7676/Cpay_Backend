package com.cpay.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cpay.entities.CreditCardApplication;
import com.cpay.entities.ERole;
import com.cpay.entities.OrderTracking;
import com.cpay.exceptions.ResourceNotFoundException;
import com.cpay.repositories.OrderTrackingRepository;

public class OrderTrackingServiceImplTest {

	@Mock
	private OrderTrackingRepository orderTrackingRepository;

	@InjectMocks
	private OrderTrackingServiceImpl orderTrackingService;

	private OrderTracking orderTracking;
	private CreditCardApplication application;

	@BeforeEach
	public void setUp() {

		MockitoAnnotations.openMocks(this);

		orderTracking = new OrderTracking();
		orderTracking.setOrderId(12345L);
		orderTracking.setOrderStatus(ERole.EOrderStatus.PENDING);
		orderTracking.setOrderDate(java.time.LocalDate.now());

		application = new CreditCardApplication();
		application.setId(1L);

		orderTracking.setCreditCardApplication(application);
	}

	@Test
	public void testTrackOrder_Success() {

		when(orderTrackingRepository.findByOrderId(12345L)).thenReturn(Optional.of(orderTracking));

		OrderTracking trackedOrder = orderTrackingService.trackOrder(12345L);

		assertNotNull(trackedOrder);
		assertEquals(orderTracking.getOrderId(), trackedOrder.getOrderId());
		assertEquals(orderTracking.getOrderStatus(), trackedOrder.getOrderStatus());

		verify(orderTrackingRepository, times(1)).findByOrderId(12345L);
	}

	@Test
	public void testTrackOrder_OrderNotFound() {

		when(orderTrackingRepository.findByOrderId(99999L)).thenReturn(Optional.empty());

		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
			orderTrackingService.trackOrder(99999L);
		});

		assertEquals("Order not found with ID: 99999", exception.getMessage());

		verify(orderTrackingRepository, times(1)).findByOrderId(99999L);
	}

	@Test
	public void testGetAllOrders() {

		when(orderTrackingRepository.findAll()).thenReturn(java.util.Collections.singletonList(orderTracking));

		java.util.List<OrderTracking> orders = orderTrackingService.getAllOrders();

		assertNotNull(orders);
		assertEquals(1, orders.size());
		assertEquals(orderTracking.getOrderId(), orders.get(0).getOrderId());

		verify(orderTrackingRepository, times(1)).findAll();
	}

	@Test
	public void testUpdateOrderStatus_Success() {

		when(orderTrackingRepository.findByOrderId(12345L)).thenReturn(Optional.of(orderTracking));

		orderTracking.setOrderStatus(ERole.EOrderStatus.COMPLETED);
		when(orderTrackingRepository.save(orderTracking)).thenReturn(orderTracking);

		OrderTracking updatedOrder = orderTrackingService.updateOrderStatus(12345L, "COMPLETED");

		assertNotNull(updatedOrder);
		assertEquals(ERole.EOrderStatus.COMPLETED, updatedOrder.getOrderStatus());

		verify(orderTrackingRepository, times(1)).findByOrderId(12345L);
		verify(orderTrackingRepository, times(1)).save(updatedOrder);
	}

	@Test
	public void testUpdateOrderStatus_OrderNotFound() {

		when(orderTrackingRepository.findByOrderId(99999L)).thenReturn(Optional.empty());

		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
			orderTrackingService.updateOrderStatus(99999L, "DISPATCHED");
		});

		assertEquals("Order not found with ID: 99999", exception.getMessage());

		verify(orderTrackingRepository, times(1)).findByOrderId(99999L);
	}

	@Test
	public void testCreateOrder() {

		when(orderTrackingRepository.save(orderTracking)).thenReturn(orderTracking);

		OrderTracking createdOrder = orderTrackingService.createOrder(orderTracking);

		assertNotNull(createdOrder);
		assertEquals(orderTracking.getOrderId(), createdOrder.getOrderId());
		assertEquals(orderTracking.getOrderStatus(), createdOrder.getOrderStatus());

		verify(orderTrackingRepository, times(1)).save(orderTracking);
	}

	@Test
	public void testDeleteOrder_Success() {

		when(orderTrackingRepository.findByOrderId(12345L)).thenReturn(Optional.of(orderTracking));

		orderTrackingService.deleteOrder(12345L);

		verify(orderTrackingRepository, times(1)).deleteById(12345L);
	}

	@Test
	public void testDeleteOrder_OrderNotFound() {

		when(orderTrackingRepository.findByOrderId(99999L)).thenReturn(Optional.empty());

		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
			orderTrackingService.deleteOrder(99999L);
		});

		assertEquals("Order not found with ID: 99999", exception.getMessage());

		verify(orderTrackingRepository, times(1)).findByOrderId(99999L);
	}

	@Test
	public void testGetOrderTrackingByApplication() {

		when(orderTrackingRepository.findByCreditCardApplication(application)).thenReturn(Optional.of(orderTracking));

		OrderTracking fetchedOrderTracking = orderTrackingService.getOrderTrackingByApplication(application);

		assertNotNull(fetchedOrderTracking);
		assertEquals(orderTracking.getOrderId(), fetchedOrderTracking.getOrderId());
		assertEquals(orderTracking.getCreditCardApplication().getId(),
				fetchedOrderTracking.getCreditCardApplication().getId());

		verify(orderTrackingRepository, times(1)).findByCreditCardApplication(application);
	}

	@Test
	public void testGetOrderTrackingByApplication_NotFound() {

		when(orderTrackingRepository.findByCreditCardApplication(application)).thenReturn(Optional.empty());

		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
			orderTrackingService.getOrderTrackingByApplication(application);
		});

		assertEquals("Order tracking not found for application: " + application.getId(), exception.getMessage());

		verify(orderTrackingRepository, times(1)).findByCreditCardApplication(application);
	}
}
