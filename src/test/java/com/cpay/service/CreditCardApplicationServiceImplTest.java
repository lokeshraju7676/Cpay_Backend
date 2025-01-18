package com.cpay.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cpay.entities.CreditCardApplication;
import com.cpay.entities.ERole.EApplicationStatus;
import com.cpay.entities.ERole.EOrderStatus;
import com.cpay.entities.OrderTracking;
import com.cpay.repositories.CreditCardApplicationRepository;
import com.cpay.repositories.OrderTrackingRepository;

@ExtendWith(MockitoExtension.class)
public class CreditCardApplicationServiceImplTest {

	@Mock
	private CreditCardApplicationRepository applicationRepository;

	@Mock
	private OrderTrackingRepository orderTrackingRepository;

	@InjectMocks
	private CreditCardApplicationServiceImpl applicationService;

	private CreditCardApplication application;
	private OrderTracking orderTracking;

	@BeforeEach
	public void setUp() {

		application = new CreditCardApplication();
		application.setId(1L);
		application.setUsername("testUser");
		application.setApplicationStatus(EApplicationStatus.PENDING);

		orderTracking = new OrderTracking();
		orderTracking.setOrderId(1001L);
		orderTracking.setOrderStatus(EOrderStatus.PENDING);
		orderTracking.setCreditCardApplication(application);
	}

	@Test
	public void testUpdateOrderStatusBasedOnApplicationStatus_Approve() {

		application.setApplicationStatus(EApplicationStatus.APPROVED);

		when(orderTrackingRepository.findByCreditCardApplication(application)).thenReturn(Optional.of(orderTracking));

		applicationService.updateOrderStatusBasedOnApplicationStatus(application);

		assertEquals(EOrderStatus.DISPATCHED, orderTracking.getOrderStatus());
	}

	@Test
	public void testUpdateOrderStatusBasedOnApplicationStatus_Rejected() {

		application.setApplicationStatus(EApplicationStatus.REJECTED);

		when(orderTrackingRepository.findByCreditCardApplication(application)).thenReturn(Optional.of(orderTracking));

		applicationService.updateOrderStatusBasedOnApplicationStatus(application);

		assertEquals(EOrderStatus.CANCELLED, orderTracking.getOrderStatus());
	}

	@Test
	public void testUpdateOrderStatusBasedOnApplicationStatus_Pending() {

		when(orderTrackingRepository.findByCreditCardApplication(application)).thenReturn(Optional.of(orderTracking));

		applicationService.updateOrderStatusBasedOnApplicationStatus(application);

		assertEquals(EOrderStatus.PENDING, orderTracking.getOrderStatus());
	}

	@Test
	public void testUpdateOrderStatusBasedOnApplicationStatus_Completed() {

		application.setApplicationStatus(EApplicationStatus.COMPLETED);

		when(orderTrackingRepository.findByCreditCardApplication(application)).thenReturn(Optional.of(orderTracking));

		applicationService.updateOrderStatusBasedOnApplicationStatus(application);

		assertEquals(EOrderStatus.COMPLETED, orderTracking.getOrderStatus());
	}
}
