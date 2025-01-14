package com.cpay.security.payload.response;

import java.time.LocalDateTime;

public class MessageResponse {
    
    private String message;
    private String status;
    private int statusCode;
    private LocalDateTime timestamp;

    // Default constructor
    public MessageResponse() {
        this.timestamp = LocalDateTime.now(); // Automatically set the timestamp
    }

    // Constructor with message and status
    public MessageResponse(String message) {
        this();
        this.message = message;
        this.status = "success"; // Default status
        this.statusCode = 200;   // Default status code for success
    }

    // Constructor with all fields
    public MessageResponse(String message, String status, int statusCode) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.status = status;
        this.statusCode = statusCode;
    }

    // Getters and setters

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "MessageResponse{" +
                "message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", statusCode=" + statusCode +
                ", timestamp=" + timestamp +
                '}';
    }
}
