package com.cpay.entities;

import java.time.LocalDate;

import com.cpay.entities.ERole.EPaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double amount; // Transaction amount

    @Column(nullable = false)
    private String transactionType; // Type of transaction: Credit, Debit

    @Column(nullable = false)
    private LocalDate transactionDate; // Date of transaction

    @Enumerated(EnumType.STRING) // Use enum for transactionStatus
    @Column(nullable = false)
    private ERole.EPaymentStatus transactionStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_card_id", nullable = false)
    private CreditCardApplication creditCard; // Associated credit card for the transaction

    public Transaction() {}

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

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public ERole.EPaymentStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(ERole.EPaymentStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }
    public CreditCardApplication getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCardApplication creditCard) {
        this.creditCard = creditCard;
    }

	public Transaction(Long id, Double amount, String transactionType, LocalDate transactionDate,
			EPaymentStatus transactionStatus, CreditCardApplication creditCard) {
		this.id = id;
		this.amount = amount;
		this.transactionType = transactionType;
		this.transactionDate = transactionDate;
		this.transactionStatus = transactionStatus;
		this.creditCard = creditCard;
	}
    
    
}
