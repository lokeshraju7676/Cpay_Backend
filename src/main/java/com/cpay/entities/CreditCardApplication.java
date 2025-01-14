package com.cpay.entities;

import java.time.LocalDate;
import com.cpay.entities.ERole.EApplicationStatus;

import jakarta.persistence.*;

@Entity
public class CreditCardApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String applicantName;

    @Column(nullable = false)
    private String applicantEmail;

    @Column(nullable = false)
    private String mobileNumber;

    @Column(nullable = false)
    private String employmentStatus;

    @Column(nullable = false)
    private Double annualIncome;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private LocalDate applicationDate;

    @Enumerated(EnumType.STRING) // Use enum for applicationStatus
    @Column(nullable = false)
    private ERole.EApplicationStatus applicationStatus;

    // Changed from @ManyToOne to @Column to store the username as a simple string
    @Column(nullable = false)
    private String username;  // Just store the username, not as an entity relationship

    // Getters and Setters
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public CreditCardApplication(Long id, String applicantName, String applicantEmail, String mobileNumber,
                                  String employmentStatus, Double annualIncome, String address, LocalDate applicationDate,
                                  EApplicationStatus applicationStatus, String username) {
        this.id = id;
        this.applicantName = applicantName;
        this.applicantEmail = applicantEmail;
        this.mobileNumber = mobileNumber;
        this.employmentStatus = employmentStatus;
        this.annualIncome = annualIncome;
        this.address = address;
        this.applicationDate = applicationDate;
        this.applicationStatus = applicationStatus;
        this.username = username;
    }
}
