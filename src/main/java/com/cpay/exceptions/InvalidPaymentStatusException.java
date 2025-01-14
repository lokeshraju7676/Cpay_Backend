package com.cpay.exceptions;

public class InvalidPaymentStatusException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidPaymentStatusException(String message) {
        super(message);
    }

    public InvalidPaymentStatusException(String message, Throwable cause) {
        super(message, cause);
    }
}
