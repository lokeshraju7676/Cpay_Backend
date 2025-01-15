package com.cpay.entities;

import java.time.LocalDate;

import com.cpay.entities.ERole.EPaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private LocalDate paymentDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EPaymentStatus paymentStatus;

    @ManyToOne
    @JoinColumn(name = "card_number", referencedColumnName = "cardNumber", nullable = false)
    private CreditCardDetails creditCard;  // Linking to CreditCardDetails by cardNumber

    public Payment() {}

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

    public EPaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(EPaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public CreditCardDetails getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCardDetails creditCard) {
        this.creditCard = creditCard;
    }

    public Payment(Long id, Double amount, LocalDate paymentDate, EPaymentStatus paymentStatus, CreditCardDetails creditCard) {
        this.id = id;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentStatus = paymentStatus;
        this.creditCard = creditCard;
    }
}
