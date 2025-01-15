package com.cpay.entities;

import java.util.Base64;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Entity
public class CreditCardDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String cardNumber;  // Unique card number for linking

    private String expirationDate;  // Expiration date of the credit card

    @Column(nullable = false)
    private String cvv;  // CVV number of the credit card (for security reasons)

    @Column(name = "cardholder_name")
    private String cardholderName;  // Store the cardholder name

    @OneToMany(mappedBy = "creditCard")
    @JsonIgnore
    private List<Payment> payments;  // Link to Payments

    @OneToMany(mappedBy = "creditCard")
    @JsonIgnore
    private List<Transaction> transactions;  // Link to Transactions

    // Encrypt CVV before saving or updating to DB
    @PrePersist
    @PreUpdate
    public void encryptCvv() {
        if (this.cvv != null) {
            this.cvv = encrypt(this.cvv); // Encrypt the CVV before persistence
        }
    }

    // Sample method for encryption (Base64 encoding, just for demonstration)
    private String encrypt(String cvv) {
        return Base64.getEncoder().encodeToString(cvv.getBytes()); // Encode CVV as Base64 for storage
    }

    // Getter and Setter methods

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }
}
