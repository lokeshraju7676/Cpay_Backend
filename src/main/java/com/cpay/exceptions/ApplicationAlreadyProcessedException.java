package com.cpay.exceptions;

public class ApplicationAlreadyProcessedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ApplicationAlreadyProcessedException(String message) {
        super(message);
    }

    public ApplicationAlreadyProcessedException(String message, Throwable cause) {
        super(message, cause);
    }
}
