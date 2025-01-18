package com.cpay.entities;

import java.time.LocalDate;

import com.cpay.entities.ERole.EApplicationStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ERole.EApplicationStatus applicationStatus;

	// One-to-One relationship with OrderTracking
	@OneToOne(mappedBy = "creditCardApplication", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private OrderTracking orderTracking; // Link to the order tracking

	@Column(nullable = false)
	private String username;

	// Constructor for initialization
	public CreditCardApplication(String applicantName, String applicantEmail, String mobileNumber,
			String employmentStatus, Double annualIncome, String address, LocalDate applicationDate,
			EApplicationStatus applicationStatus, String username) {
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

	public CreditCardApplication() {
		// TODO Auto-generated constructor stub
	}

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
}
