package com.cpay.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class PaymentExceptionHandler {

    // Handle RuntimeException
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handlePaymentNotFound(RuntimeException ex) {
        // You can customize this message based on your needs
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment not found");
    }
}
