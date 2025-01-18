package com.cpay.entities;

public enum ERole {
    // User Roles
    ROLE_CUSTOMER("Customer"),
    ROLE_ADMIN("Admin");

    private String displayName;

    // Constructor
    ERole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    // Enum for application status
    public enum EApplicationStatus {
        PENDING("Pending"),
        APPROVED("Approved"),
        REJECTED("Rejected"),
        COMPLETED("Completed"); // New status added

        private String displayName;

        // Constructor
        EApplicationStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    // Enum for order status
    public enum EOrderStatus {
        DISPATCHED("Dispatched"),
        IN_TRANSIT("In Transit"),
        DELIVERED("Delivered"),
        CANCELLED("Cancelled"),
        PENDING("Pending"),
        COMPLETED("Completed"); // New status added

        private String displayName;

        // Constructor
        EOrderStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    // Enum for payment status
    public enum EPaymentStatus {
        PENDING("Pending"),
        COMPLETED("Completed"),
        FAILED("Failed");

        private String displayName;

        // Constructor
        EPaymentStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}
