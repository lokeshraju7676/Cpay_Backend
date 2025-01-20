package com.cpay.entities;

import java.time.LocalDate;

import com.cpay.entities.ERole.EPaymentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    @JsonIgnore  // Ignore the ID in the response
    private Long id;

    @JsonProperty("amount")  // Ensure "amount" is serialized into the response as "amount"
    @Column(nullable = false)
    private Double amount;

    @JsonFormat(pattern = "yyyy-MM-dd")  // Format the date to "yyyy-MM-dd"
    @Column(nullable = false)
    @JsonProperty("paymentDate")  // Ensure "paymentDate" is serialized into the response as "paymentDate"
    private LocalDate paymentDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @JsonProperty("paymentStatus")  // Ensure "paymentStatus" is serialized into the response as "paymentStatus"
    private EPaymentStatus paymentStatus;

    @ManyToOne
    @JoinColumn(name = "card_number", referencedColumnName = "cardNumber", nullable = false)
    @JsonIgnore  // Assuming you don't want to expose card details in the response
    private CreditCardDetails creditCard; // Linking to CreditCardDetails by cardNumber

    public Payment() {
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

    public Payment(Long id, Double amount, LocalDate paymentDate, EPaymentStatus paymentStatus,
            CreditCardDetails creditCard) {
        this.id = id;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentStatus = paymentStatus;
        this.creditCard = creditCard;
    }
}
