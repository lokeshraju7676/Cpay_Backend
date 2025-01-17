package com.cpay.entities;

import java.time.LocalDate;

import com.cpay.entities.ERole.EOrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private Long orderId;  // Randomly generated Order ID

    // Getters and Setters
    
    // ... (Getters and Setters for each field)

    public OrderTracking() {
		// TODO Auto-generated constructor stub
	}

	// Constructor for initialization
    public OrderTracking(EOrderStatus orderStatus, LocalDate orderDate, Long orderId, CreditCardApplication creditCardApplication) {
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.orderId = orderId;
        this.creditCardApplication = creditCardApplication;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ERole.EOrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(ERole.EOrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(LocalDate deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public CreditCardApplication getCreditCardApplication() {
		return creditCardApplication;
	}

	public void setCreditCardApplication(CreditCardApplication creditCardApplication) {
		this.creditCardApplication = creditCardApplication;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
}
