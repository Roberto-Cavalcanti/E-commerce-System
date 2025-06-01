package com.project.ecommerce.exceptions;

public class PaymentErrorException extends RuntimeException {
    public PaymentErrorException() {
        super("Payment error: ");
    }
}
