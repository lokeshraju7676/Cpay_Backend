package com.cpay.entities;

import java.time.LocalDate;

import com.cpay.entities.ERole.EPaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private EPaymentStatus transactionStatus; // Transaction status

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "card_number", referencedColumnName = "cardNumber", nullable = false)
	@JsonIgnore
	private CreditCardDetails creditCard; // Associated credit card for the transaction

	public Transaction() {
	}

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

	public EPaymentStatus getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(EPaymentStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public CreditCardDetails getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCardDetails creditCard) {
		this.creditCard = creditCard;
	}

	public Transaction(Long id, Double amount, String transactionType, LocalDate transactionDate,
			EPaymentStatus transactionStatus, CreditCardDetails creditCard) {
		this.id = id;
		this.amount = amount;
		this.transactionType = transactionType;
		this.transactionDate = transactionDate;
		this.transactionStatus = transactionStatus;
		this.creditCard = creditCard;
	}
}
