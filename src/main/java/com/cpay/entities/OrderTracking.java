package com.cpay.entities;

import java.time.LocalDate;

import com.cpay.entities.ERole.EOrderStatus;
import jakarta.persistence.*;

@Entity
public class OrderTracking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ERole.EOrderStatus orderStatus;

	@Column(nullable = false)
	private LocalDate orderDate;

	@Column(nullable = true)
	private LocalDate deliveryDate;

	// One-to-One relationship with CreditCardApplication (Foreign Key)
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "application_id", referencedColumnName = "id", nullable = false)
	private CreditCardApplication creditCardApplication;

	@Column(nullable = false)
	private Long orderId; // Randomly generated Order ID

	// Default constructor
	public OrderTracking() {
		// Default constructor logic (if any)
	}

	// Constructor for initialization
	public OrderTracking(EOrderStatus orderStatus, LocalDate orderDate, Long orderId,
			CreditCardApplication creditCardApplication) {
		this.orderStatus = orderStatus;
		this.orderDate = orderDate;
		this.orderId = orderId;
		this.creditCardApplication = creditCardApplication;

		// Automatically set the delivery date to one week after the order date
		this.deliveryDate = orderDate.plusWeeks(1); // Adds 1 week to the orderDate
	}

	// Getter and Setter for id
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	// Getter and Setter for orderStatus
	public ERole.EOrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(ERole.EOrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	// Getter and Setter for orderDate
	public LocalDate getOrderDate() {
		return orderDate;
	}

	// In this setter, we update the deliveryDate to be one week after the orderDate
	// whenever orderDate is set.
	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
		// Automatically update deliveryDate whenever the orderDate is set
		this.deliveryDate = orderDate.plusWeeks(1);
	}

	// Getter and Setter for deliveryDate
	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(LocalDate deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	// Getter and Setter for creditCardApplication
	public CreditCardApplication getCreditCardApplication() {
		return creditCardApplication;
	}

	public void setCreditCardApplication(CreditCardApplication creditCardApplication) {
		this.creditCardApplication = creditCardApplication;
	}

	// Getter and Setter for orderId
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
}
