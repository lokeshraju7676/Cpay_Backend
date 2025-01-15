package com.cpay.entities;

import java.time.LocalDate;

import com.cpay.entities.ERole.EApplicationStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class CreditCardApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String applicantName;  // The name of the applicant/cardholder

    @Column(nullable = false)
    private String applicantEmail;  // The email of the applicant

    @Column(nullable = false)
    private String mobileNumber;  // Applicant's mobile number

    @Column(nullable = false)
    private String employmentStatus;  // Applicant's employment status

    @Column(nullable = false)
    private Double annualIncome;  // Applicant's annual income

    @Column(nullable = false)
    private String address;  // Applicant's residential address

    @Column(nullable = false)
    private LocalDate applicationDate;  // Date the application was made

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ERole.EApplicationStatus applicationStatus;  // Application status (e.g., approved, pending)

    // One-to-One relationship with OrderTracking
    @OneToOne(mappedBy = "creditCardApplication", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private OrderTracking orderTracking;  // Link to the order tracking

    @Column(nullable = false)
    private String username;  // Username of the applicant (can be used for linking with the User table)

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getApplicantEmail() {
        return applicantEmail;
    }

    public void setApplicantEmail(String applicantEmail) {
        this.applicantEmail = applicantEmail;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public Double getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(Double annualIncome) {
        this.annualIncome = annualIncome;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public ERole.EApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(ERole.EApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public OrderTracking getOrderTracking() {
        return orderTracking;
    }

    public void setOrderTracking(OrderTracking orderTracking) {
        this.orderTracking = orderTracking;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Constructor for initialization
    public CreditCardApplication(Long id, String applicantName, String applicantEmail, String mobileNumber,
                                  String employmentStatus, Double annualIncome, String address, LocalDate applicationDate,
                                  EApplicationStatus applicationStatus, OrderTracking orderTracking, String username) {
        this.id = id;
        this.applicantName = applicantName;
        this.applicantEmail = applicantEmail;
        this.mobileNumber = mobileNumber;
        this.employmentStatus = employmentStatus;
        this.annualIncome = annualIncome;
        this.address = address;
        this.applicationDate = applicationDate;
        this.applicationStatus = applicationStatus;
        this.orderTracking = orderTracking;
        this.username = username;
    }
}
