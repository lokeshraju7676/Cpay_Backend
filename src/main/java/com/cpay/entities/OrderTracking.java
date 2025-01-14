package com.cpay.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

import com.cpay.entities.ERole.EOrderStatus;

@Entity
public class OrderTracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING) // Use enum for orderStatus
    @Column(nullable = false)
    private ERole.EOrderStatus orderStatus;

    @Column(nullable = false)
    private LocalDate orderDate;

    @Column(nullable = true)
    private LocalDate deliveryDate;

    @OneToOne
    @JoinColumn(name = "application_id", nullable = false)
    private CreditCardApplication creditCardApplication;

    // Getters and Setters
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

	public OrderTracking(Long id, EOrderStatus orderStatus, LocalDate orderDate, LocalDate deliveryDate,
			CreditCardApplication creditCardApplication) {
		this.id = id;
		this.orderStatus = orderStatus;
		this.orderDate = orderDate;
		this.deliveryDate = deliveryDate;
		this.creditCardApplication = creditCardApplication;
	}
    
    
}
