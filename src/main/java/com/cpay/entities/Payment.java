package com.cpay.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

import com.cpay.entities.ERole.EPaymentStatus;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private LocalDate paymentDate;

    @Enumerated(EnumType.STRING) // Use enum for paymentStatus
    @Column(nullable = false)
    private ERole.EPaymentStatus paymentStatus;

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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public ERole.EPaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(ERole.EPaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public CreditCardApplication getCreditCardApplication() {
        return creditCardApplication;
    }

    public void setCreditCardApplication(CreditCardApplication creditCardApplication) {
        this.creditCardApplication = creditCardApplication;
    }

	public Payment(Long id, Double amount, LocalDate paymentDate, EPaymentStatus paymentStatus,
			CreditCardApplication creditCardApplication) {
		this.id = id;
		this.amount = amount;
		this.paymentDate = paymentDate;
		this.paymentStatus = paymentStatus;
		this.creditCardApplication = creditCardApplication;
	}
    
    
}
